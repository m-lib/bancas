package br.com.pos.experimental;

import java.text.DateFormat;
import java.util.Calendar;

public enum Formatador {
	
	DATA, HORARIO, DATA_HORARIO;
	
	private Formatador() {
		
	}
	
	public String formatar(Calendar calendario) {
		if (DATA.equals(this)) {
			DateFormat dia = DateFormat.getDateInstance(DateFormat.MEDIUM);
			return dia.format(calendario.getTime());
		} else if (HORARIO.equals(this)) {
			DateFormat horario = DateFormat.getTimeInstance(DateFormat.SHORT);
			return horario.format(calendario.getTime());
		} else if (DATA_HORARIO.equals(this)) {
			DateFormat diaHorario = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
			return diaHorario.format(calendario.getTime());
		}
		
		throw new IllegalArgumentException();
	}

}
