package br.com.joseleonardo.listadetarefas.model.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joseleonardo.listadetarefas.model.entity.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, UUID> {

	List<Tarefa> findByIdDoUsuario(UUID idDoUsuario);
	
}