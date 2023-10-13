package br.com.joseleonardo.listadetarefas.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joseleonardo.listadetarefas.model.entity.Tarefa;
import br.com.joseleonardo.listadetarefas.model.repository.TarefaRepository;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

	@Autowired
	private TarefaRepository tarefaRepository;

	@PostMapping
	public Tarefa criar(@RequestBody Tarefa tarefa, HttpServletRequest request) {
		UUID idUsuario = (UUID) request.getAttribute("idDoUsuario");

		tarefa.setIdDoUsuario(idUsuario);
		
		return tarefaRepository.save(tarefa);
	}

}