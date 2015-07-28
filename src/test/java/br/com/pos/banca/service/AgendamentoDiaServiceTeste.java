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

import br.com.pos.banca.dao.AgendamentoDiaDao;
import br.com.pos.banca.entidade.AgendamentoDia;
import br.com.pos.banca.service.factory.AgendamentoDiaServiceFactory;
import br.com.pos.persistencia.Paginacao;

import com.sun.net.httpserver.HttpServer;

public class AgendamentoDiaServiceTeste {
	
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
		ResourceFactory resourceFactory = new AgendamentoDiaServiceFactory(manager);

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
	
	private AgendamentoDia instanciarAgendamentoDia() {
		
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 5);

		AgendamentoDia agendamentoDia = new AgendamentoDia();
		agendamentoDia.setDia(calendar);
		
		return agendamentoDia;
	}

	@Test
	public void persistir() throws Exception {
		Collection<AgendamentoDia> agendamentoDias;
		AgendamentoDiaDao trabalhoDao = new AgendamentoDiaDao(manager);
		agendamentoDias = trabalhoDao.buscar(new AgendamentoDia(), new Paginacao());
		
		assertThat(agendamentoDias.isEmpty(), is(true));
		AgendamentoDia agendamentoDia = instanciarAgendamentoDia();
		
		AgendamentoDia response = cliente.target(TestPortProvider.generateURL("/agendamento/dia")).path("/persistir").request().post(Entity.entity(agendamentoDia, MediaType.APPLICATION_JSON), AgendamentoDia.class);
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 5);

		
		assertThat(response.getDia().get(Calendar.DAY_OF_MONTH), is(calendar.get(Calendar.DAY_OF_MONTH)));
	}

	@Test
	public void alterar() throws Exception {
		AgendamentoDia agendamentoDia = instanciarAgendamentoDia();
		
		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(manager);
		agendamentoDiaDao.persistir(agendamentoDia);
		
		AgendamentoDia recuperado = agendamentoDiaDao.obter(agendamentoDia.getCodigo());
		assertEquals(5, recuperado.getDia().get(Calendar.DAY_OF_MONTH));
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		recuperado.setDia(calendar);
		
		AgendamentoDia response = cliente.target(TestPortProvider.generateURL("/agendamento/dia")).path("/alterar").request().put(Entity.entity(recuperado, MediaType.APPLICATION_JSON), AgendamentoDia.class);
		assertThat(response.getDia().get(Calendar.DAY_OF_MONTH), is(1));
	}

	@Test
	public void excluir() throws Exception {
		AgendamentoDia agendamentoDia = instanciarAgendamentoDia();
		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(manager);
		agendamentoDiaDao.persistir(agendamentoDia);
		Collection<AgendamentoDia> agendamentoDias;
		
		agendamentoDias = agendamentoDiaDao.buscar(new AgendamentoDia(), new Paginacao());
		assertThat(agendamentoDias.isEmpty(), is(false));
		
		Integer codigo = agendamentoDia.getCodigo();
		cliente.target(TestPortProvider.generateURL("/agendamento/dia")).path("/excluir/{codigo}").resolveTemplate("codigo", codigo).request().delete();
		
		agendamentoDias = agendamentoDiaDao.buscar(new AgendamentoDia(), new Paginacao());
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 5);
		
		assertThat(agendamentoDias.isEmpty(), is(true));
	}
	
	@Test
	@SuppressWarnings("all")
	public void listar() throws Exception {
		Collection<AgendamentoDia> agendamentoDias;
		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(manager);
		agendamentoDias = agendamentoDiaDao.buscar(new AgendamentoDia(), new Paginacao());
		
		assertThat(agendamentoDias.isEmpty(), is(true));
		
		AgendamentoDia agendamentoDia = instanciarAgendamentoDia();
		agendamentoDiaDao.persistir(agendamentoDia);
		
		agendamentoDias = cliente.target(TestPortProvider.generateURL("/agendamento/dia")).request().get(Collection.class);
		assertEquals(1, agendamentoDias.size());
	}

	@Test
	@SuppressWarnings("all")
	public void buscar() throws Exception {
		AgendamentoDia agendamentoDia = instanciarAgendamentoDia();
		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(manager);
		agendamentoDiaDao.persistir(agendamentoDia);
		
		AgendamentoDia agendamentoDiaBusca = new AgendamentoDia();
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 5);
		agendamentoDiaBusca.setDia(calendar);
		
		Collection<AgendamentoDia> response = cliente.target(TestPortProvider.generateURL("/agendamento/dia")).path("/buscar").request().post(Entity.entity(agendamentoDiaBusca, MediaType.APPLICATION_JSON), Collection.class);
		
		Collection<AgendamentoDia> agendamentoDias = agendamentoDiaDao.buscar(agendamentoDiaBusca, new Paginacao());
		assertThat(agendamentoDias.isEmpty(), is(false));
		assertEquals(1, response.size()); 
	}

}
