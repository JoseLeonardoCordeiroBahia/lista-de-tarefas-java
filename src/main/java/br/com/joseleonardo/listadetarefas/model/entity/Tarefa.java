package br.com.joseleonardo.listadetarefas.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tarefa")
public class Tarefa {

	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;

	@Column(length = 50)
	private String titulo;

	private String descricao;
	private LocalDateTime dataDeInicio;
	private LocalDateTime dataDeTermino;
	private String prioridade;
	private UUID idDoUsuario;

	@CreationTimestamp
	private LocalDateTime dataDeCriacao;

	public void setTitulo(String titulo) throws Exception {
		if (titulo.length() > 50) {
			throw new Exception("O campo título deve conter no máximo 50 caracteres");
		}

		this.titulo = titulo;
	}

}