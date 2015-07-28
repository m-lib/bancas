package br.com.pos.banca.service;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.InetSocketAddress;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.plugins.server.sun.http.HttpContextBuilder;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.pos.academico.constante.Situacao;
import br.com.pos.banca.dao.AgendamentoHorarioDao;
import br.com.pos.banca.entidade.AgendamentoHorario;
import br.com.pos.banca.service.factory.AgendamentoHorarioServiceFactory;
import br.com.pos.persistencia.Paginacao;

import com.sun.net.httpserver.HttpServer;

public class AgendamentoHorarioServiceTeste {
	
	private Client cliente;
	private EntityManager manager;
	private EntityManagerFactory factory;
	
	private HttpServer server;
	private HttpContextBuilder builder;
	
	@Before
	public void iniciar() throws Exception {
		factory = Persistence.createEntityManagerFactory("bancas");
		manager = factory.createEntityManager();
		cliente = ClientBuilder.newClient();
		
		int porta = TestPortProvider.getPort();
		
		server = HttpServer.create(new InetSocketAddress(porta), 10);
		ResourceFactory resourceFactory = new AgendamentoHorarioServiceFactory(manager);

		builder = new HttpContextBuilder();
		builder.bind(server);

		builder.getDeployment().getRegistry().addResourceFactory(resourceFactory);
		server.start();
	}

	@After
	public void terminar() throws Exception {
		cliente.close(); 
		manager.close();
		factory.close();
		
		builder.cleanup();
		server.stop(0);
	}
	
	private AgendamentoHorario instanciarAgendamentoHorario() {
		Calendar calendarInicio = new GregorianCalendar();
		calendarInicio.set(Calendar.AM_PM, Calendar.HOUR);
		calendarInicio.set(Calendar.HOUR, 8);
		calendarInicio.set(Calendar.MINUTE, 30);
		calendarInicio.set(Calendar.SECOND, 00);

		Calendar calendarTermino = new GregorianCalendar();
		calendarTermino.set(Calendar.AM_PM, Calendar.HOUR);
		calendarTermino.set(Calendar.HOUR, 9);
		calendarTermino.set(Calendar.MINUTE, 10);
		calendarTermino.set(Calendar.SECOND, 00);

		AgendamentoHorario agendamentoHorario = new AgendamentoHorario();
		agendamentoHorario.setSituacao(Situacao.ATIVO);
		agendamentoHorario.setInicio(calendarInicio);
		agendamentoHorario.setTermino(calendarTermino);
		
		return agendamentoHorario;
	}

	@Test
	public void persistir() throws Exception {
		Collection<AgendamentoHorario> agendamentoHorarios;
		AgendamentoHorarioDao trabalhoDao = new AgendamentoHorarioDao(manager);
		agendamentoHorarios = trabalhoDao.buscar(new AgendamentoHorario(), new Paginacao());
		
		assertThat(agendamentoHorarios.isEmpty(), is(true));
		AgendamentoHorario agendamentoHorario = instanciarAgendamentoHorario();
		
		AgendamentoHorario response = cliente.target(TestPortProvider.generateURL("/agendamento/horario")).path("/persistir").request().post(Entity.entity(agendamentoHorario, MediaType.APPLICATION_JSON), AgendamentoHorario.class);
		
		Calendar calendarInicio = new GregorianCalendar();
		calendarInicio.set(Calendar.AM_PM, Calendar.HOUR);

		calendarInicio.set(Calendar.HOUR, 8);
		calendarInicio.set(Calendar.MINUTE, 30);
		calendarInicio.set(Calendar.SECOND, 00);

		assertThat(response.getInicio().get(Calendar.HOUR), is(calendarInicio.get(Calendar.HOUR)));
	}

	@Test
	public void alterar() throws Exception {
		AgendamentoHorario agendamentoHorario = instanciarAgendamentoHorario();
		
		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(manager);
		agendamentoHorarioDao.persistir(agendamentoHorario);
		
		AgendamentoHorario recuperado = agendamentoHorarioDao.obter(agendamentoHorario.getCodigo());
		assertEquals(8, recuperado.getInicio().get(Calendar.HOUR));
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR, 10);
		
		recuperado.setInicio(calendar);
		
		AgendamentoHorario response = cliente.target(TestPortProvider.generateURL("/agendamento/horario")).path("/alterar").request().put(Entity.entity(recuperado, MediaType.APPLICATION_JSON), AgendamentoHorario.class);
		assertThat(response.getInicio().get(Calendar.HOUR), is(10));
	}

	@Test
	public void excluir() throws Exception {
		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(manager);
		AgendamentoHorario agendamentoHorario = instanciarAgendamentoHorario();
		agendamentoHorarioDao.persistir(agendamentoHorario);
		Collection<AgendamentoHorario> agendamentoHorarios;
		
		agendamentoHorarios = agendamentoHorarioDao.buscar(new AgendamentoHorario(), new Paginacao());
		assertThat(agendamentoHorarios.isEmpty(), is(false));
		
		Integer codigo = agendamentoHorario.getCodigo();
		cliente.target(TestPortProvider.generateURL("/agendamento/horario")).path("/excluir/{codigo}").resolveTemplate("codigo", codigo).request().delete();
		
		agendamentoHorarios = agendamentoHorarioDao.buscar(new AgendamentoHorario(), new Paginacao());
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR, 8);
		
		assertThat(agendamentoHorarios.isEmpty(), is(true));
	}
	
	@Test
	@SuppressWarnings("all")
	public void listar() throws Exception {
		Collection<AgendamentoHorario> agendamentoHorarios;
		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(manager);
		agendamentoHorarios = agendamentoHorarioDao.buscar(new AgendamentoHorario(), new Paginacao());
		
		assertThat(agendamentoHorarios.isEmpty(), is(true));
		
		AgendamentoHorario agendamentoHorario = instanciarAgendamentoHorario();
		agendamentoHorarioDao.persistir(agendamentoHorario);
		
		agendamentoHorarios = cliente.target(TestPortProvider.generateURL("/agendamento/horario")).request().get(Collection.class);
		assertEquals(1, agendamentoHorarios.size());
	}

	@Test
	@SuppressWarnings("all")
	public void buscar() throws Exception {
		AgendamentoHorario agendamentoHorario = instanciarAgendamentoHorario();
		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(manager);
		agendamentoHorarioDao.persistir(agendamentoHorario);
		
		AgendamentoHorario agendamentoHorarioBusca = new AgendamentoHorario();
		
		Calendar calendarInicio = new GregorianCalendar();
		calendarInicio.set(Calendar.AM_PM, Calendar.HOUR);
		
		calendarInicio.set(Calendar.HOUR, 8);
		calendarInicio.set(Calendar.MINUTE, 30);
		calendarInicio.set(Calendar.SECOND, 00);
		
		agendamentoHorarioBusca.setInicio(calendarInicio);
		
		Collection<AgendamentoHorario> response = cliente.target(TestPortProvider.generateURL("/agendamento/horario")).path("/buscar").request().post(Entity.entity(agendamentoHorarioBusca, MediaType.APPLICATION_JSON), Collection.class);
		
		Collection<AgendamentoHorario> agendamentoHorarios = agendamentoHorarioDao.buscar(agendamentoHorarioBusca, new Paginacao());
		assertThat(agendamentoHorarios.isEmpty(), is(false));
		assertEquals(1, response.size()); 
	}

}
