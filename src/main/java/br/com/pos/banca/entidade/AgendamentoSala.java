package br.com.pos.banca.entidade;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import br.com.pos.academico.entidade.Curso;
import br.com.pos.academico.entidade.Local;

@EqualsAndHashCode(of = "codigo")
public class AgendamentoSala {
	
	@Getter @Setter
	private Integer codigo;
	
	@Getter @Setter
	private Local sala;
	
	@Getter @Setter
	private Set<Curso> cursos;
	
	public AgendamentoSala() {
		
	}

}
