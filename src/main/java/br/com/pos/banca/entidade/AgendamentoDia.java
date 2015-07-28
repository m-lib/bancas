package br.com.pos.banca.entidade;

import java.util.Calendar;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = "codigo")
public class AgendamentoDia {
	
	@Getter @Setter
	private Integer codigo;
	
	@Getter @Setter
	private Calendar dia;
	
	public AgendamentoDia() {
		
	}

}
