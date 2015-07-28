package br.com.pos.banca.service;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

import br.com.pos.academico.constante.Graduacao;
import br.com.pos.academico.constante.GrupoUsuario;
import br.com.pos.academico.constante.Situacao;
import br.com.pos.academico.entidade.Curso;
import br.com.pos.academico.entidade.Pessoa;
import br.com.pos.academico.entidade.Usuario;
import br.com.pos.banca.constante.TipoBanca;
import br.com.pos.banca.dao.BancaDao;
import br.com.pos.banca.entidade.Banca;
import br.com.pos.banca.entidade.Trabalho;
import br.com.pos.banca.service.factory.BancaServiceFactory;
import br.com.pos.persistencia.Paginacao;

import com.sun.net.httpserver.HttpServer;

public class BancaServiceTeste {
	
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
		ResourceFactory resourceFactory = new BancaServiceFactory(manager);

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
	
	private Banca preencherBancaQualificacao() {
		Banca banca = new Banca();
		banca.setTipo(TipoBanca.QUALIFICACAO);
		
		Trabalho trabalho;
		trabalho = new Trabalho();
		trabalho.setTitulo("Teste");
		trabalho.setResumo("Trabalho de Teste");
		
		Curso curso;
		curso = new Curso();
		curso.setSigla("SEG");
		curso.setCodigo("SEG");
		curso.setSituacao(Situacao.ATIVO);
		curso.setNome("Seguranca em Redes");
		
		trabalho.setCurso(curso);
		
		Pessoa docente;
		docente = new Pessoa();
		docente.setNome("Pesquisador");
		docente.setCodigo("00000000000");
		docente.setGraduacao(Graduacao.MESTRE);
		docente.setEmail("pesquisador@email.com");
		
		Usuario orientador;
		orientador = new Usuario();
		orientador.setSenha("senha");
		orientador.setPessoa(docente);
		orientador.setLogin("00000000000");
		orientador.setSituacao(Situacao.ATIVO);
		orientador.setGrupo(GrupoUsuario.DOCENTE);
		
		trabalho.setOrientador(orientador);
		
		Pessoa aluno;
		aluno = new Pessoa();
		aluno.setNome("Aluno");
		aluno.setCodigo("11111111111");
		aluno.setEmail("aluno@email.com");
		
		Usuario orientado;
		orientado = new Usuario();
		orientado.setPessoa(aluno);
		orientado.setSenha("senha");
		orientado.setLogin("11111111111");
		orientado.setSituacao(Situacao.ATIVO);
		orientado.setGrupo(GrupoUsuario.ALUNO);
		
		trabalho.adicionarAluno(orientado);
		banca.setTrabalho(trabalho);

		Set<Pessoa> membros = new HashSet<Pessoa>();
		
		Pessoa professorUm = new Pessoa();
		professorUm.setCodigo("40109160444");
		professorUm.setEmail("professor-um@teste.com");
		professorUm.setNome("Professor Um");
		membros.add(professorUm);
		
		Pessoa professorDois = new Pessoa();
		professorDois.setCodigo("78276524176");
		professorDois.setEmail("professor-dois@teste.com");
		professorDois.setNome("Professor Dois");
		membros.add(professorDois);
		
		banca.setMembros(membros);
		
		return banca;
	}
	
	/*private Banca preencherBancaDefesa() {
		Banca banca = new Banca();
		banca.setTipo(TipoBanca.DEFESA);
		
		Trabalho trabalho;
		trabalho = new Trabalho();
		trabalho.setTitulo("Teste");
		trabalho.setResumo("Trabalho de Teste 2");
		
		Curso curso;
		curso = new Curso();
		curso.setSigla("ADS");
		curso.setCodigo("ADS");
		curso.setSituacao(Situacao.ATIVO);
		curso.setNome("Análise e Desenvolvimento de Sistemas");
		
		trabalho.setCurso(curso);
		
		Pessoa docente;
		docente = new Pessoa();
		docente.setNome("Docente");
		docente.setCodigo("22222222222");
		docente.setGraduacao(Graduacao.ESPECIALISTA);
		docente.setEmail("docente@email.com");
		
		Usuario orientador;
		orientador = new Usuario();
		orientador.setSenha("senha");
		orientador.setPessoa(docente);
		orientador.setLogin("22222222222");
		orientador.setSituacao(Situacao.ATIVO);
		orientador.setGrupo(GrupoUsuario.DOCENTE);
		
		trabalho.setOrientador(orientador);
		
		Pessoa aluno;
		aluno = new Pessoa();
		aluno.setNome("Aluno");
		aluno.setCodigo("33333333333");
		aluno.setEmail("aluno@email.com");
		
		Usuario orientado;
		orientado = new Usuario();
		orientado.setPessoa(aluno);
		orientado.setSenha("senha");
		orientado.setLogin("33333333333");
		orientado.setSituacao(Situacao.ATIVO);
		orientado.setGrupo(GrupoUsuario.ALUNO);
		
		trabalho.adicionarAluno(orientado);
		banca.setTrabalho(trabalho);

		Set<Pessoa> membros = new HashSet<Pessoa>();
		
		Pessoa professorUm = new Pessoa();
		professorUm.setEmail("professor-um@teste.com");
		professorUm.setCodigo("41574165208");
		professorUm.setNome("Professor Um");
		membros.add(professorUm);
		
		Pessoa professorDois = new Pessoa();
		professorDois.setEmail("professor-dois@teste.com");
		professorDois.setNome("Professor Dois");
		professorDois.setCodigo("69851155195");
		membros.add(professorDois);
		
		banca.setMembros(membros);
		
		return banca;
	}*/
	
	@Test
	@SuppressWarnings("all")
	public void buscar() throws Exception {
		Banca banca = preencherBancaQualificacao();
		BancaDao bancaDao = new BancaDao(manager);
		bancaDao.persistir(banca);
		
		Banca exemplo = new Banca();
		exemplo.setTipo(TipoBanca.QUALIFICACAO);
		
		Collection<Banca> response = cliente.target(TestPortProvider.generateURL("/banca")).path("/buscar").request().post(Entity.entity(exemplo, MediaType.APPLICATION_JSON), Collection.class);
		
		assertThat(response.isEmpty(), is(false));
		assertEquals(1, response.size());
	}
	
	@Test
	public void persistir() throws Exception {
		Collection<Banca> bancas;
		BancaDao bancaDao = new BancaDao(manager);
		bancas = bancaDao.buscar(new Banca(), new Paginacao());
		
		assertThat(bancas.isEmpty(), is(true));
		Banca banca = preencherBancaQualificacao();
		
		Banca response = cliente.target(TestPortProvider.generateURL("/banca")).path("/persistir").request().post(Entity.entity(banca, MediaType.APPLICATION_JSON), Banca.class);
		assertThat(response.getTipo(), is(TipoBanca.QUALIFICACAO));
		
		bancas = bancaDao.buscar(new Banca(), new Paginacao());
		assertEquals(1, bancas.size());	
	}
	
	@Test
	@SuppressWarnings("all")
	public void listar() throws Exception {
		Collection<Banca> bancas;
		BancaDao bancaDao = new BancaDao(manager);
		bancas = bancaDao.buscar(new Banca(), new Paginacao());
		
		assertThat(bancas.isEmpty(), is(true));
		
		Banca banca = preencherBancaQualificacao();
		bancaDao.persistir(banca);
		
		bancas = cliente.target(TestPortProvider.generateURL("/banca")).request().get(Collection.class);
		assertEquals(1, bancas.size());
	}
	
	@Test
	public void alterar() throws Exception {
		Banca banca = preencherBancaQualificacao();
		
		BancaDao bancaDao = new BancaDao(manager);
		bancaDao.persistir(banca);
		
		Banca recuperado = bancaDao.obter(banca.getCodigo());
		assertThat(recuperado.getTipo(), is(TipoBanca.QUALIFICACAO)); 	
		
		banca.setTipo(TipoBanca.DEFESA);
		
		Banca response = cliente.target(TestPortProvider.generateURL("/banca")).path("/alterar").request().put(Entity.entity(banca, MediaType.APPLICATION_JSON), Banca.class);
		assertThat(response.getTipo(), is(TipoBanca.DEFESA));

		recuperado = bancaDao.obter(banca.getCodigo());
		assertThat(recuperado.getTipo(), is(TipoBanca.DEFESA));
	}

	@Test
	public void excluir() throws Exception {
		Banca banca = preencherBancaQualificacao();
		BancaDao bancaDao = new BancaDao(manager);
		bancaDao.persistir(banca);
		Collection<Banca> bancas;
		
		bancas = bancaDao.buscar(new Banca(), new Paginacao());
		assertThat(bancas.isEmpty(), is(false));
		
		Integer codigo = banca.getCodigo();
		cliente.target(TestPortProvider.generateURL("/banca")).path("/excluir/{codigo}").resolveTemplate("codigo", codigo).request().delete();
		
		bancas = bancaDao.buscar(new Banca(), new Paginacao());
		assertThat(bancas.isEmpty(), is(true));
	}

}
