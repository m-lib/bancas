package br.com.pos.academico.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import br.com.pos.academico.constante.Periodo;

@EqualsAndHashCode(of = "codigo")
public class Matricula {
	
	@Getter @Setter
	private Integer ano;
	
	@Getter @Setter
	private Curso curso;
	
	@Getter
	private Usuario usuario;
	
	@Getter @Setter
	private Periodo periodo;
	
	@Getter @Setter
	private Integer codigo;
	
	@Getter @Setter
	private Unidade unidade;
	
	@Getter @Setter
	private Semestre semestre;
	
	public Matricula() {
		
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
