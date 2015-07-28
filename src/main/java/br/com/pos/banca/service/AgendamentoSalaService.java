package br.com.pos.banca.service;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.pos.banca.dao.AgendamentoSalaDao;
import br.com.pos.banca.entidade.AgendamentoSala;
import br.com.pos.persistencia.Paginacao;

@Path("agendamento/sala")
public class AgendamentoSalaService {
	
private EntityManager manager;
	
	public AgendamentoSalaService() {

	}
	
	@Inject
	public AgendamentoSalaService(EntityManager manager) {
		this.manager = manager;
	}
	
	@GET
	public Response listar() throws Exception {
		AgendamentoSalaDao agendamentoSalaDao = new AgendamentoSalaDao(manager);
		Collection<AgendamentoSala> agendamentoSalas = agendamentoSalaDao.buscar(new AgendamentoSala(), new Paginacao());
		
		return Response.ok(agendamentoSalas, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/persistir")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response persistir(AgendamentoSala agendamentoSala) throws Exception {
		AgendamentoSalaDao agendamentoSalaDao = new AgendamentoSalaDao(manager);
		agendamentoSalaDao.persistir(agendamentoSala);
		
		return Response.ok(agendamentoSala, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/buscar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buscar(AgendamentoSala agendamentoSala) throws Exception {
		AgendamentoSalaDao agendamentoSalaDao = new AgendamentoSalaDao(manager);
		Collection<AgendamentoSala> agendamentoSalas = agendamentoSalaDao.buscar(agendamentoSala, new Paginacao());
		
		return Response.ok(agendamentoSalas, MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("/alterar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alterar(AgendamentoSala agendamentoSala) throws Exception {
		AgendamentoSalaDao agendamentoSalaDao = new AgendamentoSalaDao(manager);
		agendamentoSalaDao.atualizar(agendamentoSala);

		return Response.ok(agendamentoSala, MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("/excluir/{codigo}")
	public Response excluir(@PathParam("codigo") Integer codigo) throws Exception {
		AgendamentoSalaDao agendamentoSalaDao = new AgendamentoSalaDao(manager);
		AgendamentoSala agendamentoSala = agendamentoSalaDao.obter(codigo);
		agendamentoSalaDao.remover(agendamentoSala);
		
		return listar();
	}

}
