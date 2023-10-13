package br.com.joseleonardo.listadetarefas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.joseleonardo.listadetarefas.model.entity.Usuario;
import br.com.joseleonardo.listadetarefas.model.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	public ResponseEntity<?> criar(@RequestBody Usuario usuario) {
		Usuario usuarioExistente = usuarioRepository.findByNomeDeUsuario(usuario.getNomeDeUsuario());
		
		if (usuarioExistente != null) {
			// Mensagem de erro
			// Status code -> Status dentro da requisição (201 criado || 400 solicitação incorreta)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse nome de usuário já existe!");
		}
		
		String senhaCriptografada = BCrypt.withDefaults().hashToString(12, usuario.getSenha().toCharArray());
		
		usuario.setSenha(senhaCriptografada);
		
		Usuario usuarioCriado = usuarioRepository.save(usuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
	}

}