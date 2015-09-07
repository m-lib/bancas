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

import br.com.pos.banca.dao.AgendamentoDiaDao;
import br.com.pos.banca.entidade.AgendamentoDia;
import br.com.pos.persistencia.Paginacao;

public class AgendamentoDiaDaoTeste {

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
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 5);

		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(this.manager);
		AgendamentoDia agendamentoDia = new AgendamentoDia();
		agendamentoDia.setDia(calendar);

		agendamentoDiaDao.persistir(agendamentoDia);
		AgendamentoDia agendamentoDiaBusca = new AgendamentoDia();
		agendamentoDiaBusca.setDia(calendar);

		Collection<AgendamentoDia> agendamentoDiasEncontrados = agendamentoDiaDao.buscar(agendamentoDiaBusca, new Paginacao());
		assertThat(agendamentoDiasEncontrados.isEmpty(), is(false));
	}

	@Test
	public void persistir() throws Exception {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 10);

		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(this.manager);
		AgendamentoDia agendamentoDia = new AgendamentoDia();
		agendamentoDia.setDia(calendar);

		agendamentoDiaDao.persistir(agendamentoDia);
		assertNotNull((agendamentoDiaDao.obter(agendamentoDia.getCodigo())));
	}

	@Test
	public void listar() throws Exception {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 10);

		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(this.manager);
		AgendamentoDia agendamentoDia = new AgendamentoDia();
		agendamentoDia.setDia(calendar);

		agendamentoDiaDao.persistir(agendamentoDia);

		Collection<AgendamentoDia> agendamentoDiasBuscados = agendamentoDiaDao.buscar(new AgendamentoDia(), new Paginacao());
		assertThat(agendamentoDiasBuscados.isEmpty(), is(false));
	}

	@Test
	public void alterar() throws Exception {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 15);

		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(this.manager);
		AgendamentoDia agendamentoDia = new AgendamentoDia();
		agendamentoDia.setDia(calendar);

		agendamentoDiaDao.persistir(agendamentoDia);

		AgendamentoDia agendamentoDiaEncontrado = agendamentoDiaDao.obter(agendamentoDia.getCodigo());
		assertEquals(agendamentoDiaEncontrado.getDia(), agendamentoDia.getDia());

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		agendamentoDiaEncontrado.setDia(calendar);

		agendamentoDiaDao.atualizar(agendamentoDiaEncontrado);

		AgendamentoDia agendamentoDiaAlteradoEncontrado = agendamentoDiaDao.obter(agendamentoDia.getCodigo());
		assertEquals(agendamentoDiaEncontrado.getDia(), agendamentoDiaAlteradoEncontrado.getDia());
	}

	@Test
	public void excluir() throws Exception {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 15);

		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(this.manager);

		AgendamentoDia agendamentoDia = new AgendamentoDia();
		agendamentoDia.setDia(calendar);

		agendamentoDiaDao.persistir(agendamentoDia);
		AgendamentoDia agendamentoDiaEncontrado = agendamentoDiaDao.obter(agendamentoDia.getCodigo());

		assertEquals(agendamentoDiaEncontrado.getDia(), agendamentoDia.getDia());
		agendamentoDiaDao.remover(agendamentoDiaEncontrado);

		AgendamentoDia agendamentoDiaExcluidoEncontrado = agendamentoDiaDao.obter(agendamentoDia.getCodigo());
		assertNull(agendamentoDiaExcluidoEncontrado);
	}

}
