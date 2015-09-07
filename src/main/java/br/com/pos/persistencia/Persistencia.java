package br.com.pos.persistencia;

import java.util.Collection;

import javax.persistence.EntityManager;

import br.com.quiui.QuiuiBuilder;

public abstract class Persistencia<T> {	
	
	private EntityManager manager;
	private Class<T> tipo;
	
	public Persistencia(EntityManager manager, Class<T> tipo) {
		this.manager = manager;
		this.tipo = tipo;
	}
	
	public Collection<T> buscar(T entidade, Paginacao paginacao) throws Exception {
		QuiuiBuilder<T> query = new QuiuiBuilder<T>(manager, tipo);
		query.create(entidade);
		
		query.setFirst(paginacao.getPrimeiroRegistro());
		query.setMax(paginacao.getMaximo());
		return query.select();
	}
	
	public Long quantitdade(T entidade) throws Exception {
		QuiuiBuilder<T> query = new QuiuiBuilder<T>(manager, tipo);
		query.create(entidade);
		return query.count();
	}
	
	public void persistir(T entidade) {
		try {
			manager.getTransaction().begin();
			manager.persist(entidade);
			manager.getTransaction().commit();
			manager.refresh(entidade);
		} catch (Exception exception) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			
			throw exception;
		}
	}
	
	public void atualizar(T entidade) {
		try {
			manager.getTransaction().begin();
			entidade = manager.merge(entidade);
			manager.getTransaction().commit();
			manager.refresh(entidade);
		} catch (Exception exception) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			
			throw exception;
		}
	}
	
	public void remover(T entidade) {
		try {
			manager.getTransaction().begin();
			manager.remove(entidade);
			manager.getTransaction().commit();
		} catch (Exception exception) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			
			throw exception;
		}
	}

}
