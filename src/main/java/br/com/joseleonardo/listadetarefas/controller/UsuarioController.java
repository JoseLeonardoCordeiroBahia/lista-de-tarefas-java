package br.com.joseleonardo.listadetarefas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joseleonardo.listadetarefas.model.entity.Usuario;
import br.com.joseleonardo.listadetarefas.model.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	public Usuario criar(@RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

}