package com.example.crud.repository;

import com.example.crud.model.Obra;
import com.example.crud.model.TipoObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Integer> {

    @Query("""
        SELECT DISTINCT o FROM Obra o LEFT JOIN o.autores a
        WHERE (:busca IS NULL OR LOWER(o.titulo) LIKE LOWER(CONCAT('%',:busca,'%'))
                               OR LOWER(a.nome)   LIKE LOWER(CONCAT('%',:busca,'%')))
          AND (:tipo IS NULL OR o.tipoObra = :tipo)
          AND (:anoInicio IS NULL OR o.anoPublicacao >= :anoInicio)
          AND (:anoFim IS NULL OR o.anoPublicacao <= :anoFim)
          AND (:dataInicio IS NULL OR o.dataAquisicao >= :dataInicio)
          AND (:dataFim IS NULL OR o.dataAquisicao <= :dataFim)
        ORDER BY o.titulo ASC
        """)
    List<Obra> buscarComFiltros(
            @Param("busca") String busca,
            @Param("tipo") TipoObra tipo,
            @Param("anoInicio") Integer anoInicio,
            @Param("anoFim") Integer anoFim,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);

    long countByTipoObra(TipoObra tipoObra);
}
