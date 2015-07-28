package br.com.pos.academico.service;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.pos.academico.dao.CursoDao;
import br.com.pos.academico.entidade.Curso;
import br.com.pos.persistencia.Paginacao;

@Path("curso")
public class CursoService {
	
	private EntityManager manager;
	
	public CursoService() {

	}
	
	@Inject
	public CursoService(EntityManager manager) {
		this.manager = manager;
	}
	
	@GET
	public Response listar() throws Exception {
		CursoDao cursoDao = new CursoDao(manager);
		Collection<Curso> cursos = cursoDao.buscar(new Curso(), new Paginacao());
		
		return Response.ok(cursos, MediaType.APPLICATION_JSON).build();
	}

}
