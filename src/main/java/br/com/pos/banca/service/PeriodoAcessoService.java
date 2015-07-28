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

import br.com.pos.banca.constante.Acao;
import br.com.pos.banca.dao.PeriodoAcessoDao;
import br.com.pos.banca.entidade.PeriodoAcesso;
import br.com.pos.persistencia.Paginacao;

@Path("periodo/acesso")
public class PeriodoAcessoService  {
	
	private EntityManager manager;
	
	public PeriodoAcessoService() {

	}
	
	@Inject
	public PeriodoAcessoService(EntityManager manager) {
		this.manager = manager;
	}
	
	@GET
	public Response listar() throws Exception {
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		Collection<PeriodoAcesso> periodoAcessos = periodoAcessoDao.buscar(new PeriodoAcesso(), new Paginacao());
		
		return Response.ok(periodoAcessos, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/persistir")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response persistir(PeriodoAcesso periodoAcesso) throws Exception {
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		periodoAcessoDao.persistir(periodoAcesso);
		
		return Response.ok(periodoAcesso, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/buscar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buscar(PeriodoAcesso periodoAcesso) throws Exception {
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		Collection<PeriodoAcesso> periodoAcessos = periodoAcessoDao.buscar(periodoAcesso, new Paginacao());
		
		return Response.ok(periodoAcessos, MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("/alterar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alterar(PeriodoAcesso periodoAcesso) throws Exception {
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		periodoAcessoDao.atualizar(periodoAcesso);

		return Response.ok(periodoAcesso, MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("/excluir/{codigo}")
	public Response excluir(@PathParam("codigo") Acao codigo) throws Exception {
		PeriodoAcessoDao periodoAcessoDao = new PeriodoAcessoDao(manager);
		PeriodoAcesso periodoAcesso = periodoAcessoDao.obter(codigo);
		periodoAcessoDao.remover(periodoAcesso);

		return listar();
	}

}
