package br.com.pos.banca.entidade;

import java.util.Calendar;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import br.com.pos.banca.constante.Acao;

@EqualsAndHashCode(of = "codigo")
public class PeriodoAcesso {
	
	@Getter @Setter
	private Acao codigo;
	
	@Getter @Setter
	private Calendar inicio;
	
	@Getter @Setter
	private Calendar termino;
	
	public PeriodoAcesso() {
		
	}

}
