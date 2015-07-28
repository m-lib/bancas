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

import br.com.pos.banca.dao.AgendamentoDiaDao;
import br.com.pos.banca.entidade.AgendamentoDia;
import br.com.pos.persistencia.Paginacao;

@Path("agendamento/dia")
public class AgendamentoDiaService  {
	
	private EntityManager manager;
	
	public AgendamentoDiaService() {

	}
	
	@Inject
	public AgendamentoDiaService(EntityManager manager) {
		this.manager = manager;
	}
	
	@GET
	public Response listar() throws Exception {
		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(manager);
		Collection<AgendamentoDia> agendamentoDias = agendamentoDiaDao.buscar(new AgendamentoDia(), new Paginacao());
		
		return Response.ok(agendamentoDias, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/persistir")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response persistir(AgendamentoDia agendamentoDia) throws Exception {
		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(manager);
		agendamentoDiaDao.persistir(agendamentoDia);
		
		return Response.ok(agendamentoDia, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/buscar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buscar(AgendamentoDia agendamentoDia) throws Exception {
		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(manager);
		Collection<AgendamentoDia> agendamentoDias = agendamentoDiaDao.buscar(agendamentoDia, new Paginacao());
		
		return Response.ok(agendamentoDias, MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("/alterar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alterar(AgendamentoDia agendamentoDia) throws Exception {
		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(manager);
		agendamentoDiaDao.atualizar(agendamentoDia);

		return Response.ok(agendamentoDia, MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("/excluir/{codigo}")
	public Response excluir(@PathParam("codigo") Integer codigo) throws Exception {
		AgendamentoDiaDao agendamentoDiaDao = new AgendamentoDiaDao(manager);
		AgendamentoDia agendamentoDia = agendamentoDiaDao.obter(codigo);
		agendamentoDiaDao.remover(agendamentoDia);

		return listar();
	}

}
