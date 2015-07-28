package br.com.pos.academico.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.pos.academico.constante.GrupoUsuario;
import br.com.pos.academico.entidade.Pessoa;
import br.com.pos.academico.entidade.Usuario;
import br.com.pos.persistencia.Paginacao;
import br.com.pos.persistencia.Persistencia;
import br.com.quiui.QuiuiBuilder;

public class UsuarioDao extends Persistencia<Usuario> {
	
	private EntityManager manager;

	public UsuarioDao(EntityManager manager) {
		super(manager, Usuario.class);
		this.manager = manager;
	}
	
	public Usuario obter(int codigo) {
		Usuario usuario = manager.find(Usuario.class, codigo);
		if (usuario == null) throw new NoResultException();
		return usuario;
	}
	
	public Collection<Usuario> buscar(String nome, GrupoUsuario grupo, Paginacao paginacao) throws Exception {
		QuiuiBuilder<Usuario> query = new QuiuiBuilder<Usuario>(manager, Usuario.class);
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);
		
		Usuario usuario;
		usuario = new Usuario();
		usuario.setGrupo(grupo);
		usuario.setPessoa(pessoa);
		
		query.enableLikeForAttribute(Pessoa.class, "nome");
		query.setFirst(paginacao.getPrimeiroRegistro());
		query.setMax(paginacao.getMaximo());		
		query.create(usuario);
		
		return query.select();
	}
	
	public Collection<Usuario> buscarDocente(String nome, Paginacao paginacao) throws Exception {
		return buscar(nome, GrupoUsuario.DOCENTE, paginacao);
	}
	
	public Collection<Usuario> buscarAluno(String nome, Paginacao paginacao) throws Exception {
		return buscar(nome, GrupoUsuario.ALUNO, paginacao);
	}

}
