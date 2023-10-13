package br.com.joseleonardo.listadetarefas.model.entity;

import lombok.Data;

@Data // Coloca os Getters e Setters para todos os atributos
public class Usuario {

	private String nome;
	private String nomeDeUsuario;
	private String senha;

}