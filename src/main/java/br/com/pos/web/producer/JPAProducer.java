package br.com.pos.web.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAProducer {

	public JPAProducer() {
		
	}
	
	@Produces
	@SessionScoped
	public EntityManager getEntityManager(EntityManagerFactory factory) {
		return factory.createEntityManager();
	}

	public void destroy(@Disposes EntityManager manager) {
		if (manager.isOpen()) {
			manager.close();
		}
	}
	
	@Produces
	@ApplicationScoped
	public EntityManagerFactory getEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("bancas");
	}

	public void destroy(@Disposes EntityManagerFactory factory) {
		if (factory.isOpen()) {
			factory.close();
		}
	}

}
