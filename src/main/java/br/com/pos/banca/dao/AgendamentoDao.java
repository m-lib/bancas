package br.com.pos.banca.dao;

import javax.persistence.EntityManager;
import br.com.pos.banca.entidade.Agendamento;
import br.com.pos.persistencia.Persistencia;

public class AgendamentoDao extends Persistencia<Agendamento> {
	
	private EntityManager manager;
	
	public AgendamentoDao(EntityManager manager) {
		super(manager, Agendamento.class);
		this.manager = manager;
	}
	
	public Agendamento obter(int codigo) {
		return manager.find(Agendamento.class, codigo);
	}

}
