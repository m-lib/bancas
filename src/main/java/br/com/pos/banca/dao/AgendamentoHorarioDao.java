package br.com.pos.banca.dao;

import javax.persistence.EntityManager;

import br.com.pos.banca.entidade.AgendamentoHorario;
import br.com.pos.persistencia.Persistencia;

public class AgendamentoHorarioDao extends Persistencia<AgendamentoHorario> {

	private EntityManager manager;

	public AgendamentoHorarioDao(EntityManager manager) {
		super(manager, AgendamentoHorario.class);
		this.manager = manager;
	}

	public AgendamentoHorario obter(int codigo) {
		return manager.find(AgendamentoHorario.class, codigo);
	}

}
