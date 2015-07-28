package br.com.pos.academico.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import br.com.pos.academico.constante.GrupoUsuario;
import br.com.pos.academico.constante.Situacao;

@EqualsAndHashCode(of = "codigo")
public class Usuario {
	
	@Getter @Setter
	private String login;
	
	@Getter @Setter
	private String senha;
	
	@Getter @Setter
	private Pessoa pessoa;
	
	@Getter @Setter
	private Integer codigo;
	
	@Getter @Setter
	private Situacao situacao;
	
	@Getter @Setter
	private GrupoUsuario grupo;
	
	public Usuario() {
		
	}
	
	public boolean isOne(GrupoUsuario grupoUsuario) {
		return grupo.equals(grupoUsuario);
	}

}
