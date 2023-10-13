package br.com.joseleonardo.listadetarefas.model.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joseleonardo.listadetarefas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

	Usuario findByNomeDeUsuario(String nomeDeUsuario);
	
}