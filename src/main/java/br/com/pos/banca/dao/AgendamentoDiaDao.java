package br.com.pos.banca.dao;

import javax.persistence.EntityManager;

import br.com.pos.banca.entidade.AgendamentoDia;
import br.com.pos.persistencia.Persistencia;

public class AgendamentoDiaDao extends Persistencia<AgendamentoDia> {

	private EntityManager manager;

	public AgendamentoDiaDao(EntityManager manager) {
		super(manager, AgendamentoDia.class);
		this.manager = manager;
	}

	public AgendamentoDia obter(int codigo) {
		return manager.find(AgendamentoDia.class, codigo);
	}

}
