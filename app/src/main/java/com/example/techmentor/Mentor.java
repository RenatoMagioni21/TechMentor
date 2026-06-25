package com.example.techmentor;

public class Mentor {
    private Long id;
    private String nome;
    private String especialidade;
    private String disponibilidade;

    public Mentor() {}

    public String getNome() { return nome; }
    public String getEspecialidade() { return especialidade; }
    public Long getId() { return id; }
}
