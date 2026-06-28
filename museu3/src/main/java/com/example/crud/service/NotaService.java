package com.example.crud.service;

import com.example.crud.model.NotaInterna;
import com.example.crud.model.Obra;
import com.example.crud.model.Usuario;
import com.example.crud.repository.NotaInternaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotaService {

    private final NotaInternaRepository repo;

    public NotaService(NotaInternaRepository repo) { this.repo = repo; }

    public List<NotaInterna> listarPorObra(Integer obraId) {
        return repo.findByObraIdOrderByDataCriacaoDesc(obraId);
    }

    /** Notas feitas por um usuário (painel do bibliotecário). */
    public List<NotaInterna> listarPorUsuario(String username) {
        return repo.findByAutorUsernameOrderByDataCriacaoDesc(username);
    }

    @Transactional
    public void adicionar(Obra obra, Usuario autor, String conteudo) {
        NotaInterna nota = new NotaInterna();
        nota.setObra(obra); nota.setAutor(autor);
        nota.setConteudo(conteudo.trim());
        nota.setDataCriacao(LocalDateTime.now());
        repo.save(nota);
    }

    @Transactional
    public void excluir(Integer notaId) { repo.deleteById(notaId); }
}
