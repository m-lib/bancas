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

import br.com.pos.academico.constante.GrupoUsuario;
import br.com.pos.banca.dao.TrabalhoDao;
import br.com.pos.banca.entidade.Trabalho;
import br.com.pos.persistencia.Paginacao;
import br.com.pos.web.seguranca.Permissao;

@Path("trabalho")
public class TrabalhoService  {
	
	private EntityManager manager;
	
	public TrabalhoService() {

	}
	
	@Inject
	public TrabalhoService(EntityManager manager) {
		this.manager = manager;
	}
	
	@GET
	@Path("/listar")
	@Permissao(GrupoUsuario.ADMINISTRADOR)
	public Response listar() throws Exception {
		TrabalhoDao trabalhoDao = new TrabalhoDao(manager);
		Collection<Trabalho> trabalhos = trabalhoDao.buscar(new Trabalho(), new Paginacao());
		
		return Response.ok(trabalhos, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/persistir")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response persistir(Trabalho trabalho) throws Exception {
		TrabalhoDao trabalhoDao = new TrabalhoDao(manager);
		trabalhoDao.persistir(trabalho);
		
		return Response.ok(trabalho, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/buscar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buscar(Trabalho trabalho) throws Exception {
		TrabalhoDao trabalhoDao = new TrabalhoDao(manager);
		Collection<Trabalho> trabalhos = trabalhoDao.buscar(trabalho, new Paginacao());
		
		return Response.ok(trabalhos, MediaType.APPLICATION_JSON).build();
	}
	

	@PUT
	@Path("/alterar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alterar(Trabalho trabalho) throws Exception {
		TrabalhoDao trabalhoDao = new TrabalhoDao(manager);
		trabalhoDao.atualizar(trabalho);

		return Response.ok(trabalho, MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("/excluir/{codigo}")
	public Response excluir(@PathParam("codigo") Integer codigo) throws Exception {
		TrabalhoDao trabalhoDao = new TrabalhoDao(manager);
		Trabalho trabalho = trabalhoDao.obter(codigo);
		trabalhoDao.remover(trabalho);

		return Response.ok(trabalho, MediaType.APPLICATION_JSON).build();
	}

}
