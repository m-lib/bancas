package br.com.pos.academico.entidade;

public enum Semestre {
	
	PRIMEIRO,
	SEGUNDO;
	
	private Semestre() {
		
	}
	
	public static Semestre obter(String valor) {
		if (valor.trim().equals("1")) {
			return PRIMEIRO;
		} else if (valor.trim().equals("2")) {
			return SEGUNDO;
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1) + name().substring(1).toLowerCase();
	}

}
