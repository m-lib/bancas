package br.com.pos.web.seguranca;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import br.com.pos.academico.dao.UsuarioDao;
import br.com.pos.academico.entidade.Usuario;

@Provider
public class Filtro implements ContainerRequestFilter, ContainerResponseFilter {
	
	private final int LOGIN = 0;
	private final int SENHA = 1;
	
	private final String NAO_AUTENTICADO = "para acessar esse servico voce precisa estar autenticado";
	private final String NAO_AUTORIZADO = "voce nao possui as credenciais necessarias para acessar esse servico";
	
	private boolean bloquear = true;

	@Inject EntityManager manager;
	@Context ResourceInfo resource;
	
	public Filtro() {

	}

	@Override
	public void filter(ContainerRequestContext requisicao) throws IOException {
		String authorization = requisicao.getHeaderString("authorization");
		
		if (authorization == null) {
			abortarRequisicao(requisicao, NAO_AUTENTICADO);
		} else {
			authorization = authorization.replace("Basic ", "");
			
			Decoder decoder = Base64.getDecoder();
			String saida[] = new String(decoder.decode(authorization)).split(":");
			
			if (saida.length == 2) {
				String login = saida[LOGIN];
				String senha = saida[SENHA];
				
				try {
					UsuarioDao dao = new UsuarioDao(manager);
					Usuario usuario = dao.login(login, senha);
					
					if (resource.getResourceMethod().isAnnotationPresent(Permissao.class)) {
						Permissao permissao = resource.getResourceMethod().getAnnotation(Permissao.class);
						if (Arrays.asList(permissao.value()).contains(usuario.getGrupo())) {
							bloquear = false;
						} else {
							abortarRequisicao(requisicao, NAO_AUTORIZADO);
						}
					} else {
						bloquear = false;
					}
				} catch (NoResultException exception) {
					abortarRequisicao(requisicao, NAO_AUTENTICADO);
				}
			} else {
				abortarRequisicao(requisicao, NAO_AUTENTICADO);
			}
		}
	}

	@Override
	public void filter(ContainerRequestContext requisicao, ContainerResponseContext resposta) throws IOException {
		if (bloquear) {
			resposta.getHeaders().add("WWW-Authenticate", "Basic");
		}
	}

	public void abortarRequisicao(ContainerRequestContext requisicao, String mensagem) {
		bloquear = true;
		requisicao.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(mensagem).build());
	}

}
