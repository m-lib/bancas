package br.com.pos.banca.dao;

import javax.persistence.EntityManager;

import br.com.pos.banca.entidade.AgendamentoSala;
import br.com.pos.persistencia.Persistencia;

public class AgendamentoSalaDao extends Persistencia<AgendamentoSala> {

	private EntityManager manager;

	public AgendamentoSalaDao(EntityManager manager) {
		super(manager, AgendamentoSala.class);
		this.manager = manager;
	}
	
	public AgendamentoSala obter(int codigo) {
		return manager.find(AgendamentoSala.class, codigo);
	}

}
