package br.com.pos.academico.service;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.pos.academico.dao.PessoaDao;
import br.com.pos.academico.dao.UsuarioDao;
import br.com.pos.academico.entidade.Pessoa;
import br.com.pos.academico.entidade.Usuario;
import br.com.pos.persistencia.Paginacao;

@Path("pessoa")
public class PessoaService {
	
	private EntityManager manager;
	
	public PessoaService() {

	}
	
	@Inject
	public PessoaService(EntityManager manager) {
		this.manager = manager;
	}
	
	@GET
	public Response listar() throws Exception {
		PessoaDao pessoaDao = new PessoaDao(manager);
		Collection<Pessoa> pessoas = pessoaDao.buscar(new Pessoa(), new Paginacao());
		
		return Response.ok(pessoas, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/buscar/docente/{nome}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buscarDocente(@PathParam("nome") String nome) throws Exception {
		UsuarioDao usuarioDao = new UsuarioDao(manager);
		
		Collection<Usuario> usuarios = usuarioDao.buscarDocente(nome, new Paginacao(5));
		
		return Response.ok(usuarios, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/buscar/aluno/{nome}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buscarAluno(@PathParam("nome") String nome) throws Exception {
		UsuarioDao usuarioDao = new UsuarioDao(manager);
		
		Collection<Usuario> usuarios = usuarioDao.buscarAluno(nome, new Paginacao(5));
		
		return Response.ok(usuarios, MediaType.APPLICATION_JSON).build();
	}

}
