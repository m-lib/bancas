package br.com.pos.academico.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = "codigo")
public class Local {
	
	@Getter @Setter
	private Integer codigo;
	
	@Getter @Setter
	private String nome;

}
