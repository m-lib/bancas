package br.com.pos.banca.entidade;

import java.util.Calendar;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import br.com.pos.academico.constante.Situacao;

@EqualsAndHashCode(of = "codigo")
public class AgendamentoHorario {
	
	@Getter @Setter
	private Integer codigo;
	
	@Getter @Setter
	private Situacao situacao;
	
	@Getter @Setter
	@Temporal(TemporalType.TIME)
	private Calendar inicio;
	
	@Getter @Setter
	@Temporal(TemporalType.TIME)
	private Calendar termino;
	
	public AgendamentoHorario() {
		
	}

}
