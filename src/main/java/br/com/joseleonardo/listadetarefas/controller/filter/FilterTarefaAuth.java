package br.com.joseleonardo.listadetarefas.controller.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import br.com.joseleonardo.listadetarefas.model.entity.Usuario;
import br.com.joseleonardo.listadetarefas.model.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTarefaAuth extends OncePerRequestFilter {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();

		if (servletPath.equals("/tarefas")) {
			// Pegar a autenticação (nome de usuário e senha) de forma codificada
			String authorization = request.getHeader("Authorization");

			// Tira o "Basic" e deixa só o resto (calculando o tamanho da palavra e
			// removendo os espaços em branco)
			String authEncoded = authorization.substring("Basic".length()).trim();

			// Cria um decode (um array de bytes)
			byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

			// Converter o array de bytes para string
			String authString = new String(authDecoded);

			// ["joseleonardo (nomeDeUsuario)", "12345(senha)"]
			String[] credenciais = authString.split(":");
			String nomeDeUsuario = credenciais[0];
			String senha = credenciais[1];

			// Válidar se o usuário existe no banco de dados
			Usuario usuarioExistente = this.usuarioRepository.findByNomeDeUsuario(nomeDeUsuario);

			if (usuarioExistente == null) {
				response.sendError(401);
			} else { // Se o usuário existir vamos validar a senha
				// Validar se a senha está correta ou não
				Result verificarSenha = BCrypt.verifyer().verify(senha.toCharArray(), usuarioExistente.getSenha());

				if (verificarSenha.verified) { // Se a senha estiver correta seguir com o método
					// Segue viagem
					filterChain.doFilter(request, response);
				} else { // Se a senha estiver incorreta retornar o erro de não autorizado
					response.sendError(401);
				}
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

}