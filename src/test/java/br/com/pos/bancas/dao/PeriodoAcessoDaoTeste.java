package br.com.pos.bancas.dao;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.pos.banca.constante.Acao;
import br.com.pos.banca.dao.PeriodoAcessoDao;
import br.com.pos.banca.entidade.PeriodoAcesso;
import br.com.pos.persistencia.Paginacao;

public class PeriodoAcessoDaoTeste {
	
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
	 * Metodo responsavel por instanciar e preencher um PeriodoAcesso com uma acao de CADASTRAMENTO
	 * @return PeriodoAcesso
	 * @throws ParseException 
	 */
	private PeriodoAcesso preencherPeriodoAcessoCadastramento() throws ParseException {
		PeriodoAcesso periodoAcesso = new PeriodoAcesso();
		periodoAcesso.setCodigo(Acao.CADASTRAMENTO);
		
		periodoAcesso.setInicio(this.preencherCalendar("01/01/2015 19:00"));
		periodoAcesso.setTermino(this.preencherCalendar("01/01/2015 19:50"));
		
		return periodoAcesso;
	}
	
	/**
	 * Metodo responsavel por instanciar e preencher um PeriodoAcesso com uma acao de ESCOLHA
	 * @return PeriodoAcesso
	 * @throws ParseException 
	 */
	private PeriodoAcesso preencherPeriodoAcessoEscolha() throws ParseException {
		PeriodoAcesso periodoAcesso = new PeriodoAcesso();
		periodoAcesso.setCodigo(Acao.ESCOLHA);
		
		periodoAcesso.setInicio(this.preencherCalendar("01/01/2015 20:00"));
		periodoAcesso.setTermino(this.preencherCalendar("01/01/2015 20:50"));
		
		return periodoAcesso;
	}
	
	/**
	 * Metodo responsavel por preencher um Calendar atraves de uma String.
	 * @return Calendar
	 * @throws ParseException 
	 */
	private Calendar preencherCalendar(String formato) throws ParseException {
		DateFormat diaHorario = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
		diaHorario.parse(formato);
		
		return diaHorario.getCalendar();
	}
	
	/**
	 * Metodo responsavel por testar o metodo que busca um PeriodoAcesso atraves de um exemplo.
	 * @throws Exception
	 */
	@Test
	public void buscar() throws Exception {
		PeriodoAcesso periodoAcesso = preencherPeriodoAcessoCadastramento();

		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		periodoAcessoDao.persistir(periodoAcesso);
		
		PeriodoAcesso exemplo = new PeriodoAcesso();
		exemplo.setCodigo(Acao.CADASTRAMENTO);
		
		Collection<PeriodoAcesso> periodosAcesso = periodoAcessoDao.buscar(exemplo, new Paginacao());
		assertThat(periodosAcesso.isEmpty(), is(false));
	}
	
	/**
	 * Metodo responsavel por testar o metodo que insere um PeriodoAcesso no banco. 
	 * @throws Exception
	 */
	@Test
	public void persistir() throws Exception {
		PeriodoAcesso periodoAcesso = this.preencherPeriodoAcessoCadastramento();
		
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		Collection<PeriodoAcesso> periodosAcesso;
		
		periodosAcesso = periodoAcessoDao.buscar(new PeriodoAcesso(), new Paginacao());
		assertThat(periodosAcesso.isEmpty(), is(true));
		
		periodoAcessoDao.persistir(periodoAcesso);
		
		periodosAcesso = periodoAcessoDao.buscar(new PeriodoAcesso(), new Paginacao());
		assertThat(periodosAcesso.isEmpty(), is(false));	
	}
	
	/**
	 * Metodo responsavel por testar a listagem sem nenhum PeriodoAcesso previamente cadastrado. 
	 * @throws Exception
	 */
	@Test
	public void listarVazio() throws Exception {
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		Collection<PeriodoAcesso> periodosAcesso = periodoAcessoDao.buscar(new PeriodoAcesso(), new Paginacao());
		assertThat(periodosAcesso.isEmpty(), is(true));
	}

	/**
	 * Metodo responsavel por testar a listagem com dois PeriodoAcesso previamente cadastrados. 
	 * @throws Exception
	 */
	@Test
	public void listar() throws Exception {
		
		PeriodoAcesso periodoAcessoCadastramento = this.preencherPeriodoAcessoCadastramento();
		PeriodoAcesso periodoAcessoEscolha = this.preencherPeriodoAcessoEscolha();
		
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		
		periodoAcessoDao.persistir(periodoAcessoCadastramento);
		periodoAcessoDao.persistir(periodoAcessoEscolha);
		
		Collection<PeriodoAcesso> periodosAcesso = periodoAcessoDao.buscar(new PeriodoAcesso(), new Paginacao());
		assertThat(periodosAcesso.isEmpty(), is(false));
	}
	
	/**
	 * Metodo responsavel por testar o metodo que altera um PeriodoAcesso. 
	 * @throws Exception
	 */
	@Test
	public void alterar() throws Exception {
		PeriodoAcesso periodoAcesso = this.preencherPeriodoAcessoCadastramento();
		
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		
		periodoAcessoDao.persistir(periodoAcesso);
		
		PeriodoAcesso recuperado = periodoAcessoDao.obter(periodoAcesso.getCodigo());
		assertEquals(recuperado.getInicio(), this.preencherCalendar("01/01/2015 19:00"));
		assertEquals(recuperado.getTermino(), this.preencherCalendar("01/01/2015 19:50"));
		
		periodoAcesso.setInicio(this.preencherCalendar("01/01/2015 20:00"));
		periodoAcesso.setTermino(this.preencherCalendar("01/01/2015 20:50"));
		
		periodoAcessoDao.atualizar(periodoAcesso);
		
		recuperado = periodoAcessoDao.obter(periodoAcesso.getCodigo());
		assertEquals(recuperado.getInicio(), this.preencherCalendar("01/01/2015 20:00"));
		assertEquals(recuperado.getTermino(), this.preencherCalendar("01/01/2015 20:50"));
	}

	/**
	 * Metodo responsavel por testar o metodo que exclui um PeriodoAcesso.
	 * @throws Exception
	 */
	@Test
	public void excluir() throws Exception {
		PeriodoAcesso periodoAcesso = this.preencherPeriodoAcessoCadastramento();
		
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		periodoAcessoDao.persistir(periodoAcesso);
		Collection<PeriodoAcesso> periodosAcesso = periodoAcessoDao.buscar(new PeriodoAcesso(), new Paginacao());
		assertThat(periodosAcesso.isEmpty(), is(false));
		
		periodoAcessoDao.remover(periodoAcesso);
		
		periodosAcesso = periodoAcessoDao.buscar(new PeriodoAcesso(), new Paginacao());
		assertThat(periodosAcesso.isEmpty(), is(true));
	}

}
