package br.com.pos.banca.dao;

import javax.persistence.EntityManager;

import br.com.pos.banca.constante.Acao;
import br.com.pos.banca.entidade.PeriodoAcesso;
import br.com.pos.persistencia.Persistencia;

public class PeriodoAcessoDao extends Persistencia<PeriodoAcesso> {
	
	private EntityManager manager;
	
	public PeriodoAcessoDao(EntityManager manager) {
		super(manager, PeriodoAcesso.class);
		this.manager = manager;
	}
	
	public PeriodoAcesso obter(Acao codigo) {
		return manager.find(PeriodoAcesso.class, codigo);
	}

}
