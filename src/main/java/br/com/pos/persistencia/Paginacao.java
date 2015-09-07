package br.com.pos.persistencia;

import lombok.Getter;

public class Paginacao {

	private int numeroPaginas = 1;
	@Getter
	private long numeroRegistros;
	@Getter
	private int maximo = 10;
	@Getter
	private int pagina = 1;
	
	public Paginacao() {

	}

	public Paginacao(int maximo) {
		this.maximo = maximo;
	}

	public Integer getPrimeiroRegistro() {
		return (pagina - 1) * maximo;
	}

	public void setPagina(int pagina) {
		if (pagina == 0) {
			this.pagina = 1;
		} else {
			this.pagina = pagina;
		}
	}
	
	public int getProximo() {
		return pagina + 1 > numeroPaginas ? numeroPaginas : pagina + 1;
	}
	
	public int getAnterior() {
		return pagina - 1 == 0 ? 1 : pagina - 1;
	}
	
	public int getInicio() {
		if (pagina - 2 > 1) {
			if (pagina + 3 > numeroPaginas) {
				int inicio = numeroPaginas - 4;
				return inicio > 0 ? inicio : 1;
			} else {
				return pagina - 2;
			}
		} else {
			return 1;
		}
	}
	
	public int getFim() {
		if (getInicio() + 4 > numeroPaginas) {
			return numeroPaginas;
		} else {
			return getInicio() + 4;
		}
	}

	public void setNumeroRegistros(long numeroRegistros) {
		Double numeroPaginas = Math.ceil(numeroRegistros / 10.0);
		this.numeroPaginas = numeroPaginas.intValue();
		this.numeroRegistros = numeroRegistros;
	}

}
