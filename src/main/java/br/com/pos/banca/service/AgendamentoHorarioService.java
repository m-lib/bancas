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

import br.com.pos.banca.dao.AgendamentoHorarioDao;
import br.com.pos.banca.entidade.AgendamentoHorario;
import br.com.pos.persistencia.Paginacao;

@Path("agendamento/horario")
public class AgendamentoHorarioService  {
	
	private EntityManager manager;
	
	public AgendamentoHorarioService() {

	}
	
	@Inject
	public AgendamentoHorarioService(EntityManager manager) {
		this.manager = manager;
	}
	
	@GET
	public Response listar() throws Exception {
		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(manager);
		Collection<AgendamentoHorario> agendamentoHorarios = agendamentoHorarioDao.buscar(new AgendamentoHorario(), new Paginacao());
		
		return Response.ok(agendamentoHorarios, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/persistir")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response persistir(AgendamentoHorario agendamentoHorario) throws Exception {
		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(manager);
		agendamentoHorarioDao.persistir(agendamentoHorario);
		
		return Response.ok(agendamentoHorario, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/buscar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buscar(AgendamentoHorario agendamentoHorario) throws Exception {
		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(manager);
		Collection<AgendamentoHorario> agendamentoHorarios = agendamentoHorarioDao.buscar(agendamentoHorario, new Paginacao());
		
		return Response.ok(agendamentoHorarios, MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("/alterar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alterar(AgendamentoHorario agendamentoHorario) throws Exception {
		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(manager);
		agendamentoHorarioDao.atualizar(agendamentoHorario);

		return Response.ok(agendamentoHorario, MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("/excluir/{codigo}")
	public Response excluir(@PathParam("codigo") Integer codigo) throws Exception {
		AgendamentoHorarioDao agendamentoHorarioDao = new AgendamentoHorarioDao(manager);
		AgendamentoHorario agendamentoHorario = agendamentoHorarioDao.obter(codigo);
		agendamentoHorarioDao.remover(agendamentoHorario);

		return listar();
	}

}
