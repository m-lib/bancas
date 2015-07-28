package br.com.pos.banca.dao;

import javax.persistence.EntityManager;

import br.com.pos.banca.entidade.Trabalho;
import br.com.pos.persistencia.Persistencia;

public class TrabalhoDao extends Persistencia<Trabalho> {
	
	private EntityManager manager;
	
	public TrabalhoDao(EntityManager manager) {
		super(manager, Trabalho.class);
		this.manager = manager;
	}
	
	public Trabalho obter(int codigo) {
		return manager.find(Trabalho.class, codigo);
	}

}
