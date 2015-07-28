package br.com.pos.academico.constante;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

public enum GrupoUsuario {
	
	ALUNO,
	DOCENTE,
	FUNCIONARIO,
	ADMINISTRADOR,
	CONVIDADO;
	
	private GrupoUsuario() {

	}
	
	public static Collection<GrupoUsuario> listar() {
		Collection<GrupoUsuario> tipos = new TreeSet<GrupoUsuario>();
		tipos.addAll(Arrays.asList(GrupoUsuario.values()));
		tipos.remove(GrupoUsuario.ALUNO);
		return tipos;
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1) + name().substring(1).toLowerCase();
	}

}
