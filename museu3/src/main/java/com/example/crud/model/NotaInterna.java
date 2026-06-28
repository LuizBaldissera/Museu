package com.example.crud.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "nota_interna")
public class NotaInterna {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "obra_id", nullable = false)
    private Obra obra;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario autor;
    @Column(nullable = false, length = 2000)
    private String conteudo;
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @Column(name = "data_edicao")
    private LocalDateTime dataEdicao;

    public NotaInterna() {}
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Obra getObra() { return obra; }
    public void setObra(Obra obra) { this.obra = obra; }
    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public LocalDateTime getDataEdicao() { return dataEdicao; }
    public void setDataEdicao(LocalDateTime dataEdicao) { this.dataEdicao = dataEdicao; }
}
