package br.com.pos.academico.dao;

import javax.persistence.EntityManager;

import br.com.pos.academico.entidade.Curso;
import br.com.pos.persistencia.Persistencia;

public class CursoDao extends Persistencia<Curso> {

	private EntityManager manager;

	public CursoDao(EntityManager manager) {
		super(manager, Curso.class);
		this.manager = manager;
	}

	public Curso obter(String codigo) {
		return manager.find(Curso.class, codigo);
	}

}
