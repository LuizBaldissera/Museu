package com.example.crud.repository;
import com.example.crud.model.LogObra;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface LogObraRepository extends JpaRepository<LogObra, Long> {
    List<LogObra> findAllByOrderByDataHoraDesc(PageRequest pageable);
    List<LogObra> findByObraIdOrderByDataHoraDesc(Integer obraId);
}
