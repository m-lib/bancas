package br.com.pos.bancas.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
import br.com.pos.banca.constante.TipoBanca;
import br.com.pos.banca.dao.BancaDao;
import br.com.pos.banca.entidade.Banca;
import br.com.pos.banca.entidade.Trabalho;
import br.com.pos.persistencia.Paginacao;

public class BancaDaoTeste {
	
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
	
	/**
	 * Metodo responsavel por instanciar e preencher uma Banca do tipo QUALIFICACAO
	 * @return Banca
	 */
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
	
	/**
	 * Metodo responsavel por instanciar e preencher uma Banca do tipo DEFESA
	 * @return Banca
	 */
	private Banca preencherBancaDefesa() {
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
	}
	
	/**
	 * Metodo responsavel por testar o metodo que busca uma Banca atraves de um exemplo.
	 * @throws Exception
	 */
	@Test
	public void buscar() throws Exception {
		Banca banca = preencherBancaQualificacao();

		BancaDao bancaDao = new BancaDao(manager);
		bancaDao.persistir(banca);
		
		Banca exemplo = new Banca();
		exemplo.setTipo(TipoBanca.QUALIFICACAO);
		
		Collection<Banca> bancas = bancaDao.buscar(exemplo, new Paginacao());
		assertThat(bancas.isEmpty(), is(false));
	}
	
	/**
	 * Metodo responsavel por testar o metodo que insere uma Banca no banco. 
	 * @throws Exception
	 */
	@Test
	public void persistir() throws Exception {
		Banca banca = this.preencherBancaQualificacao();
		
		BancaDao bancaDao = new BancaDao(manager);
		Collection<Banca> bancas;
		
		bancas = bancaDao.buscar(new Banca(), new Paginacao());
		assertThat(bancas.isEmpty(), is(true));
		
		bancaDao.persistir(banca);
		
		bancas = bancaDao.buscar(new Banca(), new Paginacao());
		assertThat(bancas.isEmpty(), is(false));	
	}
	
	/**
	 * Metodo responsavel por testar a listagem sem nenhuma Banca previamente cadastrada. 
	 * @throws Exception
	 */
	@Test
	public void listarVazio() throws Exception {
		BancaDao bancaDao = new BancaDao(manager);
		Collection<Banca> bancas = bancaDao.buscar(new Banca(), new Paginacao());
		assertThat(bancas.isEmpty(), is(true));
	}

	/**
	 * Metodo responsavel por testar a listagem com duas Banca previamente cadastradas. 
	 * @throws Exception
	 */
	@Test
	public void listar() throws Exception {
		Banca bancaQualificacao = this.preencherBancaQualificacao();
		Banca bancaDefesa = this.preencherBancaDefesa();
		
		BancaDao bancaDao = new BancaDao(manager);
		
		bancaDao.persistir(bancaQualificacao);
		bancaDao.persistir(bancaDefesa);
		
		Collection<Banca> bancas = bancaDao.buscar(new Banca(), new Paginacao());
		assertThat(bancas.isEmpty(), is(false));
	}
	
	/**
	 * Metodo responsavel por testar o metodo que altera uma Banca. 
	 * @throws Exception
	 */
	@Test
	public void alterar() throws Exception {
		Banca banca = this.preencherBancaQualificacao();
		BancaDao bancaDao = new BancaDao(manager);
		bancaDao.persistir(banca);
		
		Banca recuperado = bancaDao.obter(banca.getCodigo());
		assertEquals(recuperado.getTipo(), TipoBanca.QUALIFICACAO);
		
		banca.setTipo(TipoBanca.DEFESA);
		bancaDao.atualizar(banca);
		
		recuperado = bancaDao.obter(banca.getCodigo());
		assertEquals(recuperado.getTipo(), TipoBanca.DEFESA);
	}

	/**
	 * Metodo responsavel por testar o metodo que exclui uma Banca.
	 * @throws Exception
	 */
	@Test
	public void excluir() throws Exception {
		Banca banca = this.preencherBancaQualificacao();
		BancaDao bancaDao = new BancaDao(manager);
		bancaDao.persistir(banca);
		
		Collection<Banca> bancas = bancaDao.buscar(new Banca(), new Paginacao());
		assertThat(bancas.isEmpty(), is(false));
		bancaDao.remover(banca);
		
		bancas = bancaDao.buscar(new Banca(), new Paginacao());
		assertThat(bancas.isEmpty(), is(true));
	}

}
