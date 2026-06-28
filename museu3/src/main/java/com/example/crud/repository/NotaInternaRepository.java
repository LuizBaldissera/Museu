package com.example.crud.repository;
import com.example.crud.model.NotaInterna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface NotaInternaRepository extends JpaRepository<NotaInterna, Integer> {
    List<NotaInterna> findByObraIdOrderByDataCriacaoDesc(Integer obraId);
    /** Notas feitas por um usuário específico — usado no painel do bibliotecário */
    List<NotaInterna> findByAutorUsernameOrderByDataCriacaoDesc(String username);
}
