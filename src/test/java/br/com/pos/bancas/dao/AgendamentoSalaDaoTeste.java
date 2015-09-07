package br.com.pos.bancas.dao;

import br.com.pos.banca.dao.AgendamentoSalaDao;

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

import br.com.pos.academico.entidade.Local;
import br.com.pos.banca.constante.TipoBanca;
import br.com.pos.banca.dao.AgendamentoDao;
import br.com.pos.banca.entidade.Agendamento;
import br.com.pos.banca.entidade.AgendamentoSala;
import br.com.pos.banca.entidade.Banca;
import br.com.pos.banca.entidade.Trabalho;
import br.com.pos.persistencia.Paginacao;

public class AgendamentoSalaDaoTeste {

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
		AgendamentoSala agendamentoSala = new AgendamentoSala();
		Trabalho trabalho = new Trabalho();

		Banca banca = new Banca();
		banca.setTipo(TipoBanca.DEFESA);
		banca.setTrabalho(trabalho);

		Local local = new Local();
		local.setNome("Sala 1");

		agendamentoSala.setSala(local);

		AgendamentoSalaDao agendamentoDao = new AgendamentoSalaDao(manager);
		agendamentoDao.persistir(agendamentoSala);

		AgendamentoSala exemplo = new AgendamentoSala();
		
		exemplo.setSala(new Local());
		exemplo.getSala().setNome("Sala 1");

		Collection<AgendamentoSala> agendamentos = agendamentoDao.buscar(exemplo, new Paginacao());
		assertThat(agendamentos.isEmpty(), is(false));
	}

	@Test
	public void persistir() throws Exception {
		Local local = new Local();
		local.setNome("Local Teste");
		AgendamentoSala sala = new AgendamentoSala();
		sala.setSala(local);

		AgendamentoSalaDao dao = new AgendamentoSalaDao(this.manager);
		
		dao.persistir(sala);

		Collection<AgendamentoSala> agendamentos = dao.buscar(new AgendamentoSala(), new Paginacao());
		agendamentos = dao.buscar(new AgendamentoSala(), new Paginacao());
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
		AgendamentoSalaDao agendamentoDao = new AgendamentoSalaDao(manager);

		Local sala = new Local();
		sala.setNome("Sala1");

		AgendamentoSala agendamento = new AgendamentoSala();		
		agendamento.setSala(sala);

		agendamentoDao.persistir(agendamento);
		AgendamentoSala recuperado;				

		recuperado = agendamentoDao.obter(agendamento.getCodigo());
		assertEquals(recuperado.getSala().getNome(), "Sala1");

		agendamento.getSala().setNome("Sala2");
		agendamentoDao.atualizar(agendamento);

		recuperado = agendamentoDao.obter(agendamento.getCodigo());
		assertEquals(recuperado.getSala().getNome(), "Sala2");
	}

	@Test
	public void excluir() throws Exception {	
		Local local = new Local();
		local.setNome("Local Teste");
		AgendamentoSala agendamentoSala = new AgendamentoSala();
		agendamentoSala.setSala(local);

		AgendamentoSalaDao agendamentoDao = new AgendamentoSalaDao(this.manager);
		agendamentoDao.persistir(agendamentoSala);
		Collection<AgendamentoSala> agendamentos;

		agendamentos = agendamentoDao.buscar(new AgendamentoSala(), new Paginacao());
		assertThat(agendamentos.isEmpty(), is(false));
		agendamentoDao.remover(agendamentoSala);

		agendamentos = agendamentoDao.buscar(new AgendamentoSala(), new Paginacao());
		assertThat(agendamentos.isEmpty(), is(true));
	}

}
