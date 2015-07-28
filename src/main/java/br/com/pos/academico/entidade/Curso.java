package br.com.pos.academico.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import br.com.pos.academico.constante.Situacao;

@EqualsAndHashCode(of = "codigo")
public class Curso {
	
	@Getter @Setter
	private String nome;
	
	@Getter @Setter
	private String sigla;
	
	@Getter @Setter
	private String codigo;
	
	@Getter @Setter
	private Situacao situacao;
	
	@Getter
	private Usuario coordenador;
	
	public Curso() {

	}

	public void setCoordenador(Usuario coordenador) {
		this.coordenador = coordenador;
	}

}
