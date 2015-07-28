package br.com.pos.banca.dao;

import javax.persistence.EntityManager;

import br.com.pos.banca.entidade.Banca;
import br.com.pos.persistencia.Persistencia;

public class BancaDao extends Persistencia<Banca> {
	
	private EntityManager manager;
	
	public BancaDao(EntityManager manager) {
		super(manager, Banca.class);
		this.manager = manager;
	}
	
	public Banca obter(Integer codigo) {
		return manager.find(Banca.class, codigo);
	}

}
