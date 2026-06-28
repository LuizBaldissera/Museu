package com.example.crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "obra")
public class Obra {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O título é obrigatório.")
    @Column(nullable = false, length = 255)
    private String titulo;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "obra_autor",
        joinColumns = @JoinColumn(name = "obra_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Autor> autores = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoObra tipoObra;

    @Column(length = 100)
    private String categoria;

    @Column(name = "ano_publicacao")
    private Integer anoPublicacao;

    @Column(name = "data_aquisicao")
    private LocalDate dataAquisicao;

    @Column(length = 20)
    private String isbn;

    @Column(length = 20)
    private String issn;

    @Column(name = "numero_paginas")
    private Integer numeroPaginas;

    @Column(length = 150)
    private String tecnica;

    @Column(length = 150)
    private String dimensoes;

    @Column(length = 200)
    private String localizacao;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(length = 3000)
    private String descricao;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void prePersist() { dataCriacao = dataAtualizacao = LocalDateTime.now(); }
    @PreUpdate
    protected void preUpdate() { dataAtualizacao = LocalDateTime.now(); }

    /** Retorna os nomes dos autores separados por vírgula. */
    public String getAutoresComoTexto() {
        return autores.stream().map(Autor::getNome)
                .reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b);
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public List<Autor> getAutores() { return autores; }
    public void setAutores(List<Autor> autores) { this.autores = autores; }
    public TipoObra getTipoObra() { return tipoObra; }
    public void setTipoObra(TipoObra tipoObra) { this.tipoObra = tipoObra; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Integer getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(Integer anoPublicacao) { this.anoPublicacao = anoPublicacao; }
    public LocalDate getDataAquisicao() { return dataAquisicao; }
    public void setDataAquisicao(LocalDate dataAquisicao) { this.dataAquisicao = dataAquisicao; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getIssn() { return issn; }
    public void setIssn(String issn) { this.issn = issn; }
    public Integer getNumeroPaginas() { return numeroPaginas; }
    public void setNumeroPaginas(Integer numeroPaginas) { this.numeroPaginas = numeroPaginas; }
    public String getTecnica() { return tecnica; }
    public void setTecnica(String tecnica) { this.tecnica = tecnica; }
    public String getDimensoes() { return dimensoes; }
    public void setDimensoes(String dimensoes) { this.dimensoes = dimensoes; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
