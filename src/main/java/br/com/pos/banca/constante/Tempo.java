package br.com.pos.banca.constante;

public enum Tempo {
	
	PRIMEIRO, SEGUNDO, TERCEIRO;
	
	private Tempo() {
		
	}
	
	public static Tempo obter(String key) {
		if (key.equalsIgnoreCase("I")) {
			return PRIMEIRO;
		} else if (key.equalsIgnoreCase("II")) {
			return SEGUNDO;
		} else if (key.equalsIgnoreCase("III")) {
			return TERCEIRO;
		}
		
		throw new IllegalArgumentException();
	}

}
