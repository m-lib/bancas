package br.com.pos.academico.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.pos.academico.constante.GrupoUsuario;
import br.com.pos.academico.constante.Situacao;
import br.com.pos.academico.entidade.Pessoa;
import br.com.pos.persistencia.Paginacao;
import br.com.pos.persistencia.Persistencia;
import br.com.quiui.QuiuiBuilder;

public class PessoaDao extends Persistencia<Pessoa> {
	
	private EntityManager manager;
	
	public PessoaDao(EntityManager manager) {
		super(manager, Pessoa.class);
		this.manager = manager;
	}
	
	public Pessoa obter(String codigo) {
		return manager.find(Pessoa.class, codigo);
	}
	
	public Pessoa carregar(Pessoa pessoa) {
		return obter(pessoa.getCodigo());
	}
	
	public Collection<Pessoa> listarPessoasComVariasMatriculasAtivas() {
		TypedQuery<Pessoa> query = manager.createNamedQuery("buscar.pessoas.varias.matriculas", Pessoa.class);
		query.setParameter("situacao", Situacao.ATIVO);
		return query.getResultList();
	}
	
	private Collection<Pessoa> buscar(String nome, GrupoUsuario grupo, Paginacao paginacao) {
		QuiuiBuilder<Pessoa> query = new QuiuiBuilder<Pessoa>(manager, Pessoa.class);
		query.like("usuarios.pessoa.nome", nome);
		query.equal("usuarios.grupo", grupo);
		
		query.setFirst(paginacao.getPrimeiroRegistro());
		query.setMax(paginacao.getMaximo());
		
		return query.select();
	}
	
	public Collection<Pessoa> buscarAluno(String nome, Paginacao paginacao) {
		return buscar(nome, GrupoUsuario.ALUNO, paginacao);
	}

}
