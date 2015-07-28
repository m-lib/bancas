package br.com.pos.banca.service.factory;

import javax.persistence.EntityManager;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import br.com.pos.banca.service.AgendamentoHorarioService;

public class AgendamentoHorarioServiceFactory implements ResourceFactory {
	
	private EntityManager manager;
	
	public AgendamentoHorarioServiceFactory(EntityManager manager) {
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
		return AgendamentoHorarioService.class;
	}
	
	@Override
	public Object createResource(HttpRequest request, HttpResponse response, ResteasyProviderFactory factory) {
		return new AgendamentoHorarioService(manager);
	}

}
