package br.com.pos.bancas.dao;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.pos.academico.constante.Graduacao;
import br.com.pos.academico.constante.GrupoUsuario;
import br.com.pos.academico.constante.Situacao;
import br.com.pos.academico.entidade.Curso;
import br.com.pos.academico.entidade.Pessoa;
import br.com.pos.academico.entidade.Usuario;
import br.com.pos.banca.dao.TrabalhoDao;
import br.com.pos.banca.entidade.Trabalho;
import br.com.pos.persistencia.Paginacao;

public class TrabalhoDaoTeste {
	
	private EntityManager manager;
	private EntityManagerFactory factory;
	
	@Before
	public void iniciar() {
		factory = Persistence.createEntityManagerFactory("bancas");
		manager = factory.createEntityManager();
	}
	
	@After
	public void terminar() {
		manager.close();
		factory.close();
	}
	
	@Test
	public void buscar() throws Exception {
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

		TrabalhoDao trabalhoDao = new TrabalhoDao(manager);
		trabalhoDao.persistir(trabalho);
		
		Trabalho exemplo = new Trabalho();
		exemplo.setTitulo("Teste");
		
		Collection<Trabalho> trabalhos = trabalhoDao.buscar(exemplo, new Paginacao());
		assertThat(trabalhos.isEmpty(), is(false));
	}
	
	@Test
	public void persistir() throws Exception {
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

		TrabalhoDao trabalhoDao = new TrabalhoDao(manager);
		Collection<Trabalho> trabalhos;
		
		trabalhos = trabalhoDao.buscar(new Trabalho(), new Paginacao());
		assertThat(trabalhos.isEmpty(), is(true));
		
		trabalhoDao.persistir(trabalho);
		
		trabalhos = trabalhoDao.buscar(new Trabalho(), new Paginacao());
		assertThat(trabalhos.isEmpty(), is(false));
		
	}

	@Test
	public void listar() throws Exception {
		TrabalhoDao trabalhoDao = new TrabalhoDao(manager);
		Collection<Trabalho> trabalhos = trabalhoDao.buscar(new Trabalho(), new Paginacao());
		assertThat(trabalhos.isEmpty(), is(true));
	}

	@Test
	public void alterar() throws Exception {
		Trabalho trabalho;
		trabalho = new Trabalho();
		trabalho.setTitulo("Titulo");
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

		TrabalhoDao trabalhoDao = new TrabalhoDao(manager);
		trabalhoDao.persistir(trabalho);
		Trabalho recuperado;
		
		recuperado = trabalhoDao.obter(trabalho.getCodigo());
		assertEquals("Titulo", recuperado.getTitulo());
		
		trabalho.setTitulo("Novo Titulo");
		trabalhoDao.atualizar(trabalho);
		
		recuperado = trabalhoDao.obter(trabalho.getCodigo());
		assertEquals("Novo Titulo", recuperado.getTitulo());
	}

	@Test
	public void excluir() throws Exception {
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

		TrabalhoDao trabalhoDao = new TrabalhoDao(manager);
		trabalhoDao.persistir(trabalho);
		Collection<Trabalho> trabalhos;
		
		trabalhos = trabalhoDao.buscar(new Trabalho(), new Paginacao());
		assertThat(trabalhos.isEmpty(), is(false));
		
		trabalhoDao.remover(trabalho);
		
		trabalhos = trabalhoDao.buscar(new Trabalho(), new Paginacao());
		assertThat(trabalhos.isEmpty(), is(true));
	}

}
