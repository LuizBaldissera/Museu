package com.example.crud.repository;
import com.example.crud.model.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface VisitaRepository extends JpaRepository<Visita, Integer> {
    Optional<Visita> findByDataVisita(LocalDate dataVisita);
    @Query("SELECT COALESCE(SUM(v.contador), 0) FROM Visita v")
    long totalHistorico();
    List<Visita> findByDataVisitaAfterOrderByDataVisitaAsc(LocalDate desde);
}
