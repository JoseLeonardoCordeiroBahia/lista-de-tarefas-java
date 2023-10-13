package br.com.joseleonardo.listadetarefas.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data // Coloca os Getters e Setters para todos os atributos
@Entity(name = "usuario") // Nomeia a tabela no banco de dados
public class Usuario {

	@Id
	@GeneratedValue(generator = "UUID") // O jakarta (JPA) gera o ID
	private UUID id;

	private String nome;

	@Column(unique = true) // Coluna com uma restrição com valor único
	private String nomeDeUsuario;

	private String senha;

	@CreationTimestamp
	private LocalDateTime dataDeCriacao;

}