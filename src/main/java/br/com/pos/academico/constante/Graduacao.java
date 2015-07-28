package br.com.pos.academico.constante;

public enum Graduacao {
	
	GRADUADO(""),
	MESTRE("Me."),
	DOUTOR("Dr."),
	DOUTORA("Dra."),
	ESPECIALISTA("Esp."),
	POS_DOUTOR("Pós Dr."),
	POS_DOUTORA("Pós Dra.");
	
	private String titulo;
	
	private Graduacao(String titulo) {
		this.titulo = titulo;
	}
	
	@Override
	public String toString() {
		return titulo;
	}

}
