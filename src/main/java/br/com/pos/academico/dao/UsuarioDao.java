package br.com.pos.academico.dao;

import javax.persistence.EntityManager;

import br.com.pos.academico.constante.Situacao;
import br.com.pos.academico.entidade.Usuario;
import br.com.pos.persistencia.Persistencia;
import br.com.quiui.QuiuiBuilder;

public class UsuarioDao extends Persistencia<Usuario> {
	
	private EntityManager manager;

	public UsuarioDao(EntityManager manager) {
		super(manager, Usuario.class);
		this.manager = manager;
	}
	
	public Usuario login(String login, String senha) {
		QuiuiBuilder<Usuario> query = new QuiuiBuilder<Usuario>(manager, Usuario.class);
		query.equal("situacao", Situacao.ATIVO);
		query.equal("login", login);
		query.equal("senha", senha);
		return query.unique();
	}

}
