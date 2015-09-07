package br.com.pos.bancas.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.pos.academico.constante.Situacao;
import br.com.pos.banca.dao.AgendamentoHorarioDao;
import br.com.pos.banca.entidade.AgendamentoHorario;
import br.com.pos.persistencia.Paginacao;

public class AgendamentoHorarioDaoTeste {

	private EntityManager manager;
	private EntityManagerFactory factory;

	@Before
	public void iniciar() {
		this.factory = Persistence.createEntityManagerFactory("bancas");
		this.manager = this.factory.createEntityManager();
	}

	@After
	public void terminar() {
		this.manager.close();
		this.factory.close();
	}

	@Test
	public void buscar() throws Exception {
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

		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(this.manager);
		AgendamentoHorario agendamentoHorario = new AgendamentoHorario();
		agendamentoHorario.setSituacao(Situacao.ATIVO);
		agendamentoHorario.setInicio(calendarInicio);
		agendamentoHorario.setTermino(calendarTermino);

		agendamentoHorarioDao.persistir(agendamentoHorario);

		AgendamentoHorario agendamentoHorarioBusca = new AgendamentoHorario();
		agendamentoHorarioBusca.setInicio(calendarInicio);

		Collection<AgendamentoHorario> agendamentoHorariosEncontrados = agendamentoHorarioDao.buscar(agendamentoHorarioBusca, new Paginacao());
		assertThat(agendamentoHorariosEncontrados.isEmpty(), is(false));
	}

	@Test
	public void persistir() throws Exception {
		Calendar calendarInicio = new GregorianCalendar();
		calendarInicio.set(Calendar.AM_PM, Calendar.HOUR);
		
		calendarInicio.set(Calendar.HOUR, 9);
		calendarInicio.set(Calendar.MINUTE, 20);
		calendarInicio.set(Calendar.SECOND, 00);

		Calendar calendarTermino = new GregorianCalendar();
		calendarTermino.set(Calendar.AM_PM, Calendar.HOUR);

		calendarTermino.set(Calendar.HOUR, 10);
		calendarTermino.set(Calendar.MINUTE, 00);
		calendarTermino.set(Calendar.SECOND, 00);

		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(this.manager);
		AgendamentoHorario agendamentoHorario = new AgendamentoHorario();
		agendamentoHorario.setSituacao(Situacao.ATIVO);
		agendamentoHorario.setTermino(calendarTermino);
		agendamentoHorario.setInicio(calendarInicio);

		agendamentoHorarioDao.persistir(agendamentoHorario);
		assertNotNull((agendamentoHorarioDao.obter(agendamentoHorario.getCodigo())));
	}

	@Test
	public void listar() throws Exception {
		Calendar calendarInicio = new GregorianCalendar();
		calendarInicio.set(Calendar.AM_PM, Calendar.HOUR);

		calendarInicio.set(Calendar.HOUR, 10);
		calendarInicio.set(Calendar.MINUTE, 10);
		calendarInicio.set(Calendar.SECOND, 00);

		Calendar calendarTermino = new GregorianCalendar();
		calendarTermino.set(Calendar.AM_PM, Calendar.HOUR);
		
		calendarTermino.set(Calendar.HOUR, 11);
		calendarTermino.set(Calendar.MINUTE, 50);
		calendarTermino.set(Calendar.SECOND, 00);

		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(this.manager);
		AgendamentoHorario agendamentoHorario = new AgendamentoHorario();
		agendamentoHorario.setSituacao(Situacao.ATIVO);
		agendamentoHorario.setTermino(calendarTermino);
		agendamentoHorario.setInicio(calendarInicio);

		agendamentoHorarioDao.persistir(agendamentoHorario);

		Collection<AgendamentoHorario> agendamentoHorariosBuscados = agendamentoHorarioDao.buscar(new AgendamentoHorario(), new Paginacao());
		assertThat(agendamentoHorariosBuscados.isEmpty(), is(false));
	}

	@Test
	public void alterar() throws Exception {
		Calendar calendarInicio = new GregorianCalendar();
		calendarInicio.set(Calendar.AM_PM, Calendar.HOUR);

		calendarInicio.set(Calendar.HOUR, 10);
		calendarInicio.set(Calendar.MINUTE, 10);
		calendarInicio.set(Calendar.SECOND, 00);

		Calendar calendarTermino = new GregorianCalendar();
		calendarTermino.set(Calendar.AM_PM, Calendar.HOUR);
		
		calendarTermino.set(Calendar.HOUR, 11);
		calendarTermino.set(Calendar.MINUTE, 50);
		calendarTermino.set(Calendar.SECOND, 00);

		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(this.manager);

		AgendamentoHorario agendamentoHorario = new AgendamentoHorario();
		agendamentoHorario.setSituacao(Situacao.ATIVO);
		agendamentoHorario.setTermino(calendarTermino);
		agendamentoHorario.setInicio(calendarInicio);

		agendamentoHorarioDao.persistir(agendamentoHorario);

		AgendamentoHorario agendamentoHorarioEncontrado = agendamentoHorarioDao.obter(agendamentoHorario.getCodigo());
		assertEquals(agendamentoHorarioEncontrado.getTermino(), agendamentoHorario.getTermino());
		agendamentoHorarioEncontrado.setSituacao(Situacao.INATIVO);

		agendamentoHorarioDao.atualizar(agendamentoHorarioEncontrado);
		AgendamentoHorario agendamentoHorarioAlteradoEncontrado = agendamentoHorarioDao.obter(agendamentoHorario.getCodigo());
		assertEquals(agendamentoHorarioAlteradoEncontrado.getTermino(), agendamentoHorarioAlteradoEncontrado.getTermino());
	}

	@Test
	public void excluir() throws Exception {
		Calendar calendarInicio = new GregorianCalendar();
		calendarInicio.set(Calendar.AM_PM, Calendar.HOUR);

		calendarInicio.set(Calendar.HOUR, 12);
		calendarInicio.set(Calendar.MINUTE, 00);
		calendarInicio.set(Calendar.SECOND, 00);

		Calendar calendarTermino = new GregorianCalendar();
		calendarTermino.set(Calendar.AM_PM, Calendar.HOUR);
		
		calendarTermino.set(Calendar.HOUR, 12);
		calendarTermino.set(Calendar.MINUTE, 50);
		calendarTermino.set(Calendar.SECOND, 00);

		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(this.manager);
		AgendamentoHorario agendamentoHorario = new AgendamentoHorario();
		agendamentoHorario.setSituacao(Situacao.ATIVO);
		agendamentoHorario.setTermino(calendarTermino);
		agendamentoHorario.setInicio(calendarInicio);

		agendamentoHorarioDao.persistir(agendamentoHorario);

		AgendamentoHorario agendamentoHorarioEncontrado = agendamentoHorarioDao.obter(agendamentoHorario.getCodigo());
		assertEquals(agendamentoHorarioEncontrado.getTermino(), agendamentoHorario.getTermino());
		agendamentoHorarioDao.remover(agendamentoHorarioEncontrado);

		AgendamentoHorario agendamentoHorarioExcluidoEncontrado = agendamentoHorarioDao.obter(agendamentoHorario.getCodigo());
		assertNull(agendamentoHorarioExcluidoEncontrado);
	}

}
