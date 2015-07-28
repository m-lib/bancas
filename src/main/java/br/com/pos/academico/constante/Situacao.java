package br.com.pos.academico.constante;

public enum Situacao {
	
	INATIVO, ATIVO;

	private Situacao() {

	}
	
	public static Situacao obter(String valor) {
		return valor.equalsIgnoreCase("Em Curso") ? ATIVO : INATIVO;
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1) + name().substring(1).toLowerCase();
	}

}
