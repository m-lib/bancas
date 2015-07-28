package br.com.pos.academico.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = "codigo")
public class Unidade {
	
	@Getter @Setter
	private String nome;
	
	@Getter @Setter
	private String codigo;
	
	@Getter
	private Usuario diretor;
	
	public Unidade() {
		
	}

	public void setDiretor(Usuario diretor) {
		this.diretor = diretor;
	}

}
