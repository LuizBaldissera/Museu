package com.example.crud.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "visita")
public class Visita {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "data_visita", unique = true, nullable = false)
    private LocalDate dataVisita;
    private long contador;

    public Visita() {}
    public Visita(LocalDate dataVisita, long contador) { this.dataVisita = dataVisita; this.contador = contador; }
    public Integer getId() { return id; }
    public LocalDate getDataVisita() { return dataVisita; }
    public void setDataVisita(LocalDate dataVisita) { this.dataVisita = dataVisita; }
    public long getContador() { return contador; }
    public void setContador(long contador) { this.contador = contador; }
    public void incrementar() { this.contador++; }
}
