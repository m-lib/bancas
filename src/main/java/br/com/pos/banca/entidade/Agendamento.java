package br.com.pos.banca.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = "codigo")
public class Agendamento {
	
	@Getter @Setter
	private Integer codigo;
	
	@Getter @Setter
	private Banca banca;
	
	@Getter @Setter
	private AgendamentoSala sala;
	
	@Getter @Setter
	private AgendamentoDia dia;
	
	@Getter @Setter
	private AgendamentoHorario horario;
	
	public Agendamento() {
		
	}

}
