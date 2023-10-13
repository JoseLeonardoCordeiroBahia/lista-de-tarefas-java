package br.com.joseleonardo.listadetarefas.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> criar(@RequestBody Tarefa tarefa, HttpServletRequest request) {
		Object idUsuario = request.getAttribute("idDoUsuario");
		
		tarefa.setIdDoUsuario((UUID) idUsuario);
		
		LocalDateTime dataAtual = LocalDateTime.now();
		
		// 10/11/2023 - dataAtual
		// 10/10/2023 - dataDeInicio
		if (tarefa.getDataDeInicio().isBefore(dataAtual) || tarefa.getDataDeTermino().isBefore(dataAtual)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("A data de início / data de término deve ser maior do que a data atual");
		}
		
		if (tarefa.getDataDeInicio().isAfter(tarefa.getDataDeTermino())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("A data de início deve ser menor do que a data de término");
		}
		
		Tarefa tarefaCriada = tarefaRepository.save(tarefa);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(tarefaCriada);
	}
	
}