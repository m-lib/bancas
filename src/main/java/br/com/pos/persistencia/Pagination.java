package br.com.pos.persistencia;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Pagination<T> {
	
	@Getter @Setter
	private Collection<T> elementos;
	@Getter @Setter
	private long quantidade;
	@Getter @Setter
	private int maximo = 1;
	@Getter
	private int pagina = 1;
	@Getter @Setter
	private T elemento;
	
	public Pagination() {

	}

	public Pagination(int maximo) {
		this.maximo = maximo;
	}

	@JsonIgnore
	public Integer getPrimeiroRegistro() {
		return (pagina - 1) * maximo;
	}

	public void setPagina(int pagina) {
		if (pagina < 1) {
			this.pagina = 1;
		} else {
			this.pagina = pagina;
		}
	}

}
