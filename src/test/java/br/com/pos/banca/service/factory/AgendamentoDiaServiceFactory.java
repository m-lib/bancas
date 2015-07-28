package br.com.pos.banca.service.factory;

import javax.persistence.EntityManager;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import br.com.pos.banca.service.AgendamentoDiaService;

public class AgendamentoDiaServiceFactory implements ResourceFactory {
	
	private EntityManager manager;
	
	public AgendamentoDiaServiceFactory(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public void unregistered() {
		
	}
	
	@Override
	public void requestFinished(HttpRequest request, HttpResponse response, Object resource) {
		
	}
	
	@Override
	public void registered(ResteasyProviderFactory factory) {
		
	}
	
	@Override
	public Class<?> getScannableClass() {
		return AgendamentoDiaService.class;
	}
	
	@Override
	public Object createResource(HttpRequest request, HttpResponse response, ResteasyProviderFactory factory) {
		return new AgendamentoDiaService(manager);
	}

}
