package br.com.pos.experimental;

import java.util.Arrays;
import java.util.Calendar;

public class Commons {
	
	public static String converter(Calendar calendario, Formatador formatador) {
		if (calendario == null) {
			return new String();
		}
		return formatador.formatar(calendario);
	}
	
	public static String normalizarNomeProprio(String nome) {
		if (vazio(nome)) {
			return nome;
		}
		
		String normalizado = new String();
		for (String parte : normalizarEspacamento(nome).split(" ")) {
			if (!normalizado.isEmpty()) {
				normalizado += " ";
			}
			
			if (!Arrays.asList("e", "da", "de", "do", "das", "dos").contains(parte.toLowerCase())) {
				normalizado += parte.substring(0, 1) + parte.substring(1).toLowerCase();
			} else {
				normalizado += parte.toLowerCase();
			}
		}
		
		return normalizado;
	}
	
	public static boolean vazio(Object valor) {
		if (valor == null) {
			return true;
		}
		
		if (Iterable.class.isInstance(valor)) {
			Iterable<?> iterable = (Iterable<?>) valor;
			return !iterable.iterator().hasNext();
		}
		
		if (String.class.isInstance(valor) || Character.class.isInstance(valor)) {
			return Commons.removerEspacamento(valor.toString()).isEmpty();
		}
		
		return false;
	}
	
	public static boolean preenchido(Object valor) {
		return !vazio(valor);
	}
	
	public static String removerEspacamento(String valor) {
		return valor.trim().replaceAll(" +", "");
	}
	
	public static String normalizarEspacamento(String valor) {
		return valor.trim().replaceAll(" +", " ");
	}
	
}
