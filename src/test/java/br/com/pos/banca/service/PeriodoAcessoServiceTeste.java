package br.com.pos.banca.service;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
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

import br.com.pos.banca.constante.Acao;
import br.com.pos.banca.dao.PeriodoAcessoDao;
import br.com.pos.banca.entidade.PeriodoAcesso;
import br.com.pos.banca.service.factory.PeriodoAcessoServiceFactory;
import br.com.pos.persistencia.Paginacao;

import com.sun.net.httpserver.HttpServer;

public class PeriodoAcessoServiceTeste {
	
	private Client cliente;
	private EntityManager manager;
	private EntityManagerFactory factory;
	
	private HttpServer server;
	private HttpContextBuilder builder;
	
	@Before
	public void iniciar() throws IOException {
		factory = Persistence.createEntityManagerFactory("bancas");
		manager = factory.createEntityManager();
		cliente = ClientBuilder.newClient();
		
		int porta = TestPortProvider.getPort();
		
		server = HttpServer.create(new InetSocketAddress(porta), 10);
		ResourceFactory resourceFactory = new PeriodoAcessoServiceFactory(manager);

		builder = new HttpContextBuilder();
		builder.bind(server);

		builder.getDeployment().getRegistry().addResourceFactory(resourceFactory);
		server.start();
	}
	
	@After
	public void terminar() {
		cliente.close(); 
		manager.close();
		factory.close();
		
		builder.cleanup();
		server.stop(0);
	}
	
	private PeriodoAcesso preencherPeriodoAcessoCadastramento() throws ParseException {
		PeriodoAcesso periodoAcesso = new PeriodoAcesso();
		periodoAcesso.setCodigo(Acao.CADASTRAMENTO);
		
		periodoAcesso.setInicio(this.preencherCalendar("01/01/2015 19:00"));
		periodoAcesso.setTermino(this.preencherCalendar("01/01/2015 19:50"));
		
		return periodoAcesso;
	}
	
	/*private PeriodoAcesso preencherPeriodoAcessoEscolha() throws ParseException {
		PeriodoAcesso periodoAcesso = new PeriodoAcesso();
		periodoAcesso.setCodigo(Acao.ESCOLHA);
		
		periodoAcesso.setInicio(this.preencherCalendar("01/01/2015 20:00"));
		periodoAcesso.setTermino(this.preencherCalendar("01/01/2015 20:50"));
		
		return periodoAcesso;
	}*/
	
	private Calendar preencherCalendar(String formato) throws ParseException {
		DateFormat diaHorario = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
		diaHorario.parse(formato);
		
		return diaHorario.getCalendar();
	}
	
	@Test
	@SuppressWarnings("all")
	public void buscar() throws Exception {
		PeriodoAcesso periodoAcesso = preencherPeriodoAcessoCadastramento();
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		periodoAcessoDao.persistir(periodoAcesso);
		
		PeriodoAcesso exemplo = new PeriodoAcesso();
		exemplo.setCodigo(Acao.CADASTRAMENTO);
		
		Collection<PeriodoAcesso> response = cliente.target(TestPortProvider.generateURL("/periodo/acesso")).path("/buscar").request().post(Entity.entity(periodoAcesso, MediaType.APPLICATION_JSON), Collection.class);
		
		Collection<PeriodoAcesso> periodoAcessos = periodoAcessoDao.buscar(exemplo, new Paginacao());
		assertThat(periodoAcessos.isEmpty(), is(false));
		assertEquals(1, response.size());
	}
	
	@Test
	public void persistir() throws Exception {
		Collection<PeriodoAcesso> periodoAcessos;
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		periodoAcessos = periodoAcessoDao.buscar(new PeriodoAcesso(), new Paginacao());
		
		assertThat(periodoAcessos.isEmpty(), is(true));
		PeriodoAcesso periodoAcesso = preencherPeriodoAcessoCadastramento();
		
		PeriodoAcesso response = cliente.target(TestPortProvider.generateURL("/periodo/acesso")).path("/persistir").request().post(Entity.entity(periodoAcesso, MediaType.APPLICATION_JSON), PeriodoAcesso.class);
		assertThat(response.getCodigo(), is(Acao.CADASTRAMENTO));
		
		periodoAcessos = periodoAcessoDao.buscar(new PeriodoAcesso(), new Paginacao());
		assertEquals(1, periodoAcessos.size());	
	}
	
	@Test
	@SuppressWarnings("all")
	public void listar() throws Exception {
		Collection<PeriodoAcesso> periodoAcessos;
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		periodoAcessos = periodoAcessoDao.buscar(new PeriodoAcesso(), new Paginacao());
		
		assertThat(periodoAcessos.isEmpty(), is(true));
		
		PeriodoAcesso periodoAcesso = preencherPeriodoAcessoCadastramento();
		periodoAcessoDao.persistir(periodoAcesso);
		
		periodoAcessos = cliente.target(TestPortProvider.generateURL("/periodo/acesso")).request().get(Collection.class);
		assertEquals(1, periodoAcessos.size());
	}
	
	@Test
	public void alterar() throws Exception {
		PeriodoAcesso periodoAcesso = preencherPeriodoAcessoCadastramento();
		
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		periodoAcessoDao.persistir(periodoAcesso);
		
		PeriodoAcesso recuperado = periodoAcessoDao.obter(periodoAcesso.getCodigo());
		assertEquals(recuperado.getInicio(), this.preencherCalendar("01/01/2015 19:00"));
		assertEquals(recuperado.getTermino(), this.preencherCalendar("01/01/2015 19:50"));
		
		periodoAcesso.setInicio(this.preencherCalendar("01/01/2015 20:00"));
		periodoAcesso.setTermino(this.preencherCalendar("01/01/2015 20:50"));
		
		PeriodoAcesso response = cliente.target(TestPortProvider.generateURL("/periodo/acesso")).path("/alterar").request().put(Entity.entity(periodoAcesso, MediaType.APPLICATION_JSON), PeriodoAcesso.class);
		
		assertEquals(response.getInicio(), this.preencherCalendar("01/01/2015 20:00"));
		assertEquals(response.getTermino(), this.preencherCalendar("01/01/2015 20:50"));
	}

	@Test
	public void excluir() throws Exception {
		PeriodoAcesso periodoAcesso = preencherPeriodoAcessoCadastramento();
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		periodoAcessoDao.persistir(periodoAcesso);
		Collection<PeriodoAcesso> periodoAcessos;
		
		periodoAcessos = periodoAcessoDao.buscar(new PeriodoAcesso(), new Paginacao());
		assertThat(periodoAcessos.isEmpty(), is(false));
		
		Acao codigo = periodoAcesso.getCodigo();
		cliente.target(TestPortProvider.generateURL("/periodo/acesso")).path("/excluir/{codigo}").resolveTemplate("codigo", codigo).request().delete();
		
		periodoAcessos = periodoAcessoDao.buscar(new PeriodoAcesso(), new Paginacao());
		assertThat(periodoAcessos.isEmpty(), is(true));
	}

}
