package br.com.pos.banca.constante;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

public enum Dia {
	
	DOM,
	SEG,
	TER,
	QUA,
	QUI,
	SEX,
	SAB;
	
	private Dia() {
		
	}
	
	public static Dia obter(String key) {
		if (key.equalsIgnoreCase(DOM.name())) {
			return DOM;
		} else if (key.equalsIgnoreCase(SEG.name())) {
			return SEG;
		} else if (key.equalsIgnoreCase(TER.name())) {
			return TER;
		} else if (key.equalsIgnoreCase(QUA.name())) {
			return QUA;
		} else if (key.equalsIgnoreCase(QUI.name())) {
			return QUI;
		} else if (key.equalsIgnoreCase(SEX.name())) {
			return SEX;
		} else if (key.equalsIgnoreCase(SAB.name())) {
			return SAB;
		}
		
		throw new IllegalArgumentException();
	}
	
	public boolean isTer() {
		return this.equals(TER);
	}
	
	public boolean isQui() {
		return this.equals(QUI);
	}
	
	public static Collection<Dia> uteis() {
		Collection<Dia> dias = new TreeSet<Dia>();
		dias.addAll(Arrays.asList(Dia.values()));
		dias.remove(DOM);
		return dias;
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1) + name().substring(1).toLowerCase();
	}

}
