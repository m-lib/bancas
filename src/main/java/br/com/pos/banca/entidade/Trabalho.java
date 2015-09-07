package br.com.pos.banca.entidade;

import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import br.com.pos.academico.entidade.Curso;
import br.com.pos.academico.entidade.Usuario;

@EqualsAndHashCode(of = "codigo")
public class Trabalho {

	@Getter @Setter
	private Integer codigo;
	
	@Getter @Setter
	private Curso curso;
	
	@Getter @Setter
	private String titulo;
	
	@Getter @Setter
	private String resumo;
	
	@Getter
	private Usuario orientador;
	
	@Getter @Setter
	private Set<Usuario> alunos;
	
	public Trabalho() {
		
	}
	
	public void adicionarAluno(Usuario autor) {
		if (alunos == null) {
			alunos = new HashSet<Usuario>();
		}
		
		alunos.add(autor);
	}
	
	public void setOrientador(Usuario orientador) {
		this.orientador = orientador;
	}

}
