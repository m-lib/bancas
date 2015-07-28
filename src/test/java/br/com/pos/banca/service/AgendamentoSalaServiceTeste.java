package br.com.pos.banca.service;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.InetSocketAddress;
import java.util.Collection;

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

import br.com.pos.academico.entidade.Local;
import br.com.pos.banca.dao.AgendamentoSalaDao;
import br.com.pos.banca.entidade.AgendamentoSala;
import br.com.pos.banca.service.factory.AgendamentoSalaServiceFactory;
import br.com.pos.persistencia.Paginacao;

import com.sun.net.httpserver.HttpServer;

public class AgendamentoSalaServiceTeste {
	
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
		ResourceFactory resourceFactory = new AgendamentoSalaServiceFactory(manager);

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
	
	private AgendamentoSala instanciarAgendamentoSala() {
		AgendamentoSala agendamentoSala = new AgendamentoSala();

		Local local = new Local();
		local.setNome("Sala 1");

		agendamentoSala.setSala(local);

		return agendamentoSala;
	}

	@Test
	public void persistir() throws Exception {
		Collection<AgendamentoSala> agendamentoSalas;
		AgendamentoSalaDao agendamentoSalaDao = new AgendamentoSalaDao(manager);
		agendamentoSalas = agendamentoSalaDao.buscar(new AgendamentoSala(), new Paginacao());
		
		assertThat(agendamentoSalas.isEmpty(), is(true));
		AgendamentoSala agendamentoSala = instanciarAgendamentoSala();
		
		AgendamentoSala response = cliente.target(TestPortProvider.generateURL("/agendamento/sala")).path("/persistir").request().post(Entity.entity(agendamentoSala, MediaType.APPLICATION_JSON), AgendamentoSala.class);
		assertThat(response.getSala().getNome(), is("Sala 1"));
		
		agendamentoSalas = agendamentoSalaDao.buscar(new AgendamentoSala(), new Paginacao());
		assertEquals(1, agendamentoSalas.size());
	}

	@Test
	public void alterar() throws Exception {
		AgendamentoSala agendamentoSala = instanciarAgendamentoSala();
		
		AgendamentoSalaDao agendamentoSalaDao = new AgendamentoSalaDao(manager);
		agendamentoSalaDao.persistir(agendamentoSala);
		
		AgendamentoSala recuperado = agendamentoSalaDao.obter(agendamentoSala.getCodigo());
		assertEquals("Sala 1", recuperado.getSala().getNome());
		
		agendamentoSala.getSala().setNome("Sala 2");
		
		AgendamentoSala response = cliente.target(TestPortProvider.generateURL("/agendamento/sala")).path("/alterar").request().put(Entity.entity(agendamentoSala, MediaType.APPLICATION_JSON), AgendamentoSala.class);
		assertThat(response.getSala().getNome(), is("Sala 2"));
		
		recuperado = agendamentoSalaDao.obter(agendamentoSala.getCodigo());
		assertEquals("Sala 2", recuperado.getSala().getNome());
	}

	@Test
	public void excluir() throws Exception {
		AgendamentoSalaDao agendamentoSalaDao = new AgendamentoSalaDao(manager);
		AgendamentoSala agendamentoSala = instanciarAgendamentoSala();
		agendamentoSalaDao.persistir(agendamentoSala);
		Collection<AgendamentoSala> agendamentoSalas;
		
		agendamentoSalas = agendamentoSalaDao.buscar(new AgendamentoSala(), new Paginacao());
		assertThat(agendamentoSalas.isEmpty(), is(false));
		
		Integer codigo = agendamentoSala.getCodigo();
		cliente.target(TestPortProvider.generateURL("/agendamento/sala")).path("/excluir/{codigo}").resolveTemplate("codigo", codigo).request().delete();
		
		agendamentoSalas = agendamentoSalaDao.buscar(new AgendamentoSala(), new Paginacao());
		assertThat(agendamentoSalas.isEmpty(), is(true));
	}
	
	@Test
	@SuppressWarnings("all")
	public void listar() throws Exception {
		Collection<AgendamentoSala> agendamentoSalas;
		AgendamentoSalaDao agendamentoSalaDao = new AgendamentoSalaDao(manager);
		agendamentoSalas = agendamentoSalaDao.buscar(new AgendamentoSala(), new Paginacao());
		
		assertThat(agendamentoSalas.isEmpty(), is(true));
		
		AgendamentoSala agendamentoSala = instanciarAgendamentoSala();
		agendamentoSalaDao.persistir(agendamentoSala);
		
		agendamentoSalas = cliente.target(TestPortProvider.generateURL("/agendamento/sala")).request().get(Collection.class);
		assertEquals(1, agendamentoSalas.size());
	}

	@Test
	@SuppressWarnings("all")
	public void buscar() throws Exception {
		AgendamentoSalaDao agendamentoSalaDao = new AgendamentoSalaDao(manager);
		AgendamentoSala agendamentoSala = instanciarAgendamentoSala();
		agendamentoSalaDao.persistir(agendamentoSala);
		
		AgendamentoSala exemplo = new AgendamentoSala();
		
		exemplo.setSala(new Local());
		exemplo.getSala().setNome("Sala 2");
		
		Collection<AgendamentoSala> response = cliente.target(TestPortProvider.generateURL("/agendamento/sala")).path("/buscar").request().post(Entity.entity(exemplo, MediaType.APPLICATION_JSON), Collection.class);

		assertThat(response.isEmpty(), is(true));
		assertEquals(0, response.size());
	}

}
