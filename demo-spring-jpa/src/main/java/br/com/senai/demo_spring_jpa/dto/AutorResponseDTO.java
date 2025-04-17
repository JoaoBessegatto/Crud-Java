package br.com.senai.demo_spring_jpa.dto;

import br.com.senai.demo_spring_jpa.entity.Autor;

public class  AutorResponseDTO {
    private Long id;
    private String nome;
    private String sobrenome;

    public AutorResponseDTO(Autor autor) {
        this.id = autor.getId();
        this.nome = autor.getNome();
        this.sobrenome = autor.getSobrenome();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getSobrenome() { return sobrenome; }
}

