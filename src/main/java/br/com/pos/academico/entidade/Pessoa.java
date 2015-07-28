package br.com.pos.academico.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import br.com.pos.academico.constante.Graduacao;
import br.com.pos.experimental.Commons;

@EqualsAndHashCode(of = "codigo")
public class Pessoa {
	
	@Getter @Setter
	private String codigo;
	
	@Getter @Setter
	private Graduacao graduacao;
	
	@Getter @Setter
	private String email;
	
	@Getter
	private String nome;
	
	public Pessoa() {

	}

	public void setNome(String nome) {
		this.nome = Commons.normalizarNomeProprio(nome);
	}

}
