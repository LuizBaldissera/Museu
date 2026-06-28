package com.example.crud.service;

import com.example.crud.model.Visita;
import com.example.crud.repository.VisitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class VisitaService {
    private final VisitaRepository repo;
    public VisitaService(VisitaRepository repo) { this.repo = repo; }

    @Transactional
    public void registrarVisita() {
        LocalDate hoje = LocalDate.now();
        Visita v = repo.findByDataVisita(hoje).orElseGet(() -> new Visita(hoje, 0));
        v.incrementar();
        repo.save(v);
    }
    public long visitasHoje()   { return repo.findByDataVisita(LocalDate.now()).map(Visita::getContador).orElse(0L); }
    public long totalHistorico(){ return repo.totalHistorico(); }
    public List<Visita> ultimos30Dias() { return repo.findByDataVisitaAfterOrderByDataVisitaAsc(LocalDate.now().minusDays(30)); }
}
