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

import br.com.pos.banca.dao.AgendamentoDao;
import br.com.pos.banca.entidade.Agendamento;
import br.com.pos.persistencia.Paginacao;

@Path("agendamento")
public class AgendamentoService {
	
private EntityManager manager;
	
	public AgendamentoService() {

	}
	
	@Inject
	public AgendamentoService(EntityManager manager) {
		this.manager = manager;
	}
	
	@GET
	public Response listar() throws Exception {
		AgendamentoDao agendamentoDao = new AgendamentoDao(manager);
		Collection<Agendamento> agendamentos = agendamentoDao.buscar(new Agendamento(), new Paginacao());
		
		return Response.ok(agendamentos, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/persistir")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response persistir(Agendamento agendamento) throws Exception {
		AgendamentoDao agendamentoDao = new AgendamentoDao(manager);
		agendamentoDao.persistir(agendamento);
		
		return Response.ok(agendamento, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/buscar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buscar(Agendamento agendamento) throws Exception {
		AgendamentoDao agendamentoDao = new AgendamentoDao(manager);
		Collection<Agendamento> agendamentos = agendamentoDao.buscar(agendamento, new Paginacao());
		
		return Response.ok(agendamentos, MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("/alterar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alterar(Agendamento agendamento) throws Exception {
		AgendamentoDao agendamentoDao = new AgendamentoDao(manager);
		agendamentoDao.atualizar(agendamento);

		return Response.ok(agendamento, MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("/excluir/{codigo}")
	public Response excluir(@PathParam("codigo") Integer codigo) throws Exception {
		AgendamentoDao agendamentoDao = new AgendamentoDao(manager);
		Agendamento agendamento = agendamentoDao.obter(codigo);
		agendamentoDao.remover(agendamento);

		return listar();
	}

}
