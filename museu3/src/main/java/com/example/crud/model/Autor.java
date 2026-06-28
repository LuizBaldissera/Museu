package com.example.crud.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 150, unique = true)
    private String nome;
    @ManyToMany(mappedBy = "autores")
    private List<Obra> obras = new ArrayList<>();

    public Autor() {}
    public Autor(String nome) { this.nome = nome; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Obra> getObras() { return obras; }
    @Override public String toString() { return nome; }
}
