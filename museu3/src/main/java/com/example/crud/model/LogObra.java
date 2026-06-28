package com.example.crud.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_obra")
public class LogObra {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "obra_id")
    private Integer obraId;
    @Column(name = "titulo_obra", length = 255)
    private String tituloObra;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAcao acao;
    @Column(length = 3000)
    private String detalhes;
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    public LogObra() {}
    public Long getId() { return id; }
    public Integer getObraId() { return obraId; }
    public void setObraId(Integer obraId) { this.obraId = obraId; }
    public String getTituloObra() { return tituloObra; }
    public void setTituloObra(String tituloObra) { this.tituloObra = tituloObra; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public TipoAcao getAcao() { return acao; }
    public void setAcao(TipoAcao acao) { this.acao = acao; }
    public String getDetalhes() { return detalhes; }
    public void setDetalhes(String detalhes) { this.detalhes = detalhes; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
