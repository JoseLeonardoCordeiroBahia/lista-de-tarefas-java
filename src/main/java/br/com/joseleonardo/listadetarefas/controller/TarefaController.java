package br.com.joseleonardo.listadetarefas.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joseleonardo.listadetarefas.model.entity.Tarefa;
import br.com.joseleonardo.listadetarefas.model.repository.TarefaRepository;
import br.com.joseleonardo.listadetarefas.util.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

	@Autowired
	private TarefaRepository tarefaRepository;

	@PostMapping
	public ResponseEntity<?> criar(@RequestBody Tarefa tarefa, HttpServletRequest request) {
		UUID idDoUsuario = (UUID) request.getAttribute("idUsuario");
		
		tarefa.setIdDoUsuario(idDoUsuario);

		LocalDateTime dataAtual = LocalDateTime.now();

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

	@GetMapping
	public List<Tarefa> listar(HttpServletRequest request) {
		UUID idDoUsuario = (UUID) request.getAttribute("idUsuario");
		
		List<Tarefa> tarefas = tarefaRepository.findByIdDoUsuario((UUID) idDoUsuario);
		
		return tarefas;
	}

	// http://localhost:8080/tarefas/idDaTarefa
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable UUID id, @RequestBody Tarefa tarefa, HttpServletRequest request) {
		// Retorna a tarefa se existir se não retorna null
		Tarefa tarefaExistente = this.tarefaRepository.findById(id).orElse(null);

		if (tarefaExistente == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada");
		}

		UUID idDoUsuario = (UUID) request.getAttribute("idUsuario");

		if (!tarefaExistente.getIdDoUsuario().equals(idDoUsuario)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Usuário não tem permissão para alterar essa tarefa");
		}

		// Executa nossa classe Utils para ignorar as propriedades nulas e "mesclar" as informações
		Utils.copyNonNullProperties(tarefa, tarefaExistente);

		Tarefa tarefaAtualizada = tarefaRepository.save(tarefaExistente);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tarefaAtualizada);
	}

}