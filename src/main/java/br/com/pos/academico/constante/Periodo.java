package br.com.pos.academico.constante;

public enum Periodo {
	
	MATUTINO, VESPERTINO, NOTURNO;
	
	private Periodo() {

	}
	
	public static Periodo converter(String valor) {
		if ("M".equalsIgnoreCase(valor) || "MANHÃ".equalsIgnoreCase(valor)) {
			return MATUTINO;
		} else if ("T".equalsIgnoreCase(valor) || "TARDE".equalsIgnoreCase(valor)) {
			return VESPERTINO;
		} else if ("N".equalsIgnoreCase(valor) || "NOITE".equalsIgnoreCase(valor)) {
			return NOTURNO;
		}
		
		throw new IllegalArgumentException();
	}
	
	public boolean isVespertino() {
		return this.equals(VESPERTINO);
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1) + name().substring(1).toLowerCase();
	}

}
