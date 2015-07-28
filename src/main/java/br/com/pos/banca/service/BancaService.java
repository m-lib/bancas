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

import br.com.pos.banca.dao.BancaDao;
import br.com.pos.banca.entidade.Banca;
import br.com.pos.persistencia.Paginacao;

@Path("banca")
public class BancaService  {
	
	private EntityManager manager;
	
	public BancaService() {

	}
	
	@Inject
	public BancaService(EntityManager manager) {
		this.manager = manager;
	}
	
	@GET
	public Response listar() throws Exception {
		BancaDao bancaDao = new BancaDao(manager);
		Collection<Banca> bancas = bancaDao.buscar(new Banca(), new Paginacao());
		
		return Response.ok(bancas, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/persistir")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response persistir(Banca banca) throws Exception {
		BancaDao bancaDao = new BancaDao(manager);
		bancaDao.persistir(banca);
		
		return Response.ok(banca, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/buscar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buscar(Banca banca) throws Exception {
		BancaDao bancaDao = new BancaDao(manager);
		Collection<Banca> bancas = bancaDao.buscar(banca, new Paginacao());
		
		return Response.ok(bancas, MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("/alterar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alterar(Banca banca) throws Exception {
		BancaDao bancaDao = new BancaDao(manager);
		bancaDao.atualizar(banca);

		return Response.ok(banca, MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("/excluir/{codigo}")
	public Response excluir(@PathParam("codigo") Integer codigo) throws Exception {
		BancaDao bancaDao = new BancaDao(manager);
		Banca banca = bancaDao.obter(codigo);
		bancaDao.remover(banca);

		return listar();
	}

}
