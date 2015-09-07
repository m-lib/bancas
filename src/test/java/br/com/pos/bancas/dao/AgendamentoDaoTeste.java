package br.com.pos.bancas.dao;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.pos.academico.constante.Situacao;
import br.com.pos.academico.entidade.Local;
import br.com.pos.banca.dao.AgendamentoDao;
import br.com.pos.banca.entidade.Agendamento;
import br.com.pos.banca.entidade.AgendamentoDia;
import br.com.pos.banca.entidade.AgendamentoHorario;
import br.com.pos.banca.entidade.AgendamentoSala;
import br.com.pos.persistencia.Paginacao;

public class AgendamentoDaoTeste {

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
		Agendamento agendamento;
		agendamento = new Agendamento();		
		
		AgendamentoDao agendamentoDao = new AgendamentoDao(manager);
		agendamentoDao.persistir(agendamento);

		Agendamento exemplo = new Agendamento();

		Collection<Agendamento> agendamentos = agendamentoDao.buscar(exemplo, new Paginacao());
		assertThat(agendamentos.isEmpty(), is(false));
	}
	
	@Test
	public void persistir() throws Exception {
		Agendamento agendamento = new Agendamento();		
		AgendamentoDao dao = new AgendamentoDao(this.manager);

		Collection<Agendamento> agendamentos = dao.buscar(new Agendamento(), new Paginacao());
		assertThat(agendamentos.isEmpty(), is(true));

		dao.persistir(agendamento);
		agendamentos = dao.buscar(new Agendamento(), new Paginacao());

		assertThat(agendamentos.isEmpty(), is(false));
	}

	@Test
	public void listar() throws Exception {
		AgendamentoDao agendamentoDao = new AgendamentoDao(manager);
		Collection<Agendamento> agendamentos = agendamentoDao.buscar(new Agendamento(), new Paginacao());
		assertThat(agendamentos.isEmpty(), is(true));
	}

	@Test
	public void alterar() throws Exception {
		AgendamentoDao agendamentoDao = new AgendamentoDao(manager);
		AgendamentoHorario horario = new AgendamentoHorario();

		horario.setSituacao(Situacao.ATIVO);
		horario.setInicio(Calendar.getInstance());
		horario.setTermino(Calendar.getInstance());

		AgendamentoDia dia = new AgendamentoDia();
		dia.setDia(Calendar.getInstance());

		Local local = new Local();
		local.setNome("Local Teste");
		AgendamentoSala sala = new AgendamentoSala();
		sala.setSala(local);

		Agendamento agendamento = new Agendamento();		
		agendamento.setDia(dia);
		agendamento.setHorario(horario);
		agendamento.setSala(sala);		
		
		agendamentoDao.persistir(agendamento);
		Agendamento recuperado;
		
		recuperado = agendamentoDao.obter(agendamento.getCodigo());
		assertEquals(recuperado.getSala().getSala().getNome(), "Local Teste");
		
		agendamento.getSala().getSala().setNome("Novo Local");
		agendamentoDao.atualizar(agendamento);
		
		recuperado = agendamentoDao.obter(agendamento.getCodigo());
		assertEquals(recuperado.getSala().getSala().getNome(), "Novo Local");
	}

	@Test
	public void excluir() throws Exception {
		Agendamento agendamento = new Agendamento();
		AgendamentoDao agendamentoDao = new AgendamentoDao(this.manager);

		agendamentoDao.persistir(agendamento);
		Collection<Agendamento> agendamentos;

		agendamentos = agendamentoDao.buscar(new Agendamento(), new Paginacao());
		assertThat(agendamentos.isEmpty(), is(false));
		agendamentoDao.remover(agendamento);
		
		agendamentos = agendamentoDao.buscar(new Agendamento(), new Paginacao());
		assertThat(agendamentos.isEmpty(), is(true));
	}

}
