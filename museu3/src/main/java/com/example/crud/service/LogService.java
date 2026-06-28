package com.example.crud.service;

import com.example.crud.model.*;
import com.example.crud.repository.LogObraRepository;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class LogService {

    private final LogObraRepository logRepo;
    private final UsuarioRepository usuarioRepo;

    public LogService(LogObraRepository logRepo, UsuarioRepository usuarioRepo) {
        this.logRepo = logRepo; this.usuarioRepo = usuarioRepo;
    }

    @Transactional
    public void registrarCriacao(Obra obra) { salvar(obra.getId(), obra.getTitulo(), TipoAcao.CRIAR, "Obra adicionada ao acervo."); }

    @Transactional
    public void registrarEdicao(Obra antiga, Obra nova) {
        String d = detectarAlteracoes(antiga, nova);
        salvar(nova.getId(), nova.getTitulo(), TipoAcao.EDITAR, d.isEmpty() ? "Sem alterações detectadas." : d);
    }

    @Transactional
    public void registrarExclusao(Obra obra) { salvar(null, obra.getTitulo(), TipoAcao.EXCLUIR, "Obra removida do acervo."); }

    public List<LogObra> getUltimos(int qtd) { return logRepo.findAllByOrderByDataHoraDesc(PageRequest.of(0, qtd)); }
    public List<LogObra> getTodos()          { return logRepo.findAllByOrderByDataHoraDesc(PageRequest.of(0, 1000)); }
    public List<LogObra> getLogsDeObra(Integer id) { return logRepo.findByObraIdOrderByDataHoraDesc(id); }

    private void salvar(Integer obraId, String titulo, TipoAcao acao, String detalhes) {
        LogObra log = new LogObra();
        log.setObraId(obraId); log.setTituloObra(titulo);
        log.setAcao(acao); log.setDetalhes(detalhes);
        log.setDataHora(LocalDateTime.now());
        usuarioLogado().ifPresent(log::setUsuario);
        logRepo.save(log);
    }

    private String detectarAlteracoes(Obra a, Obra n) {
        List<String> diff = new ArrayList<>();
        cmp(diff, "Título",      a.getTitulo(),                    n.getTitulo());
        cmp(diff, "Tipo",        str(a.getTipoObra()),              str(n.getTipoObra()));
        cmp(diff, "Categoria",   a.getCategoria(),                  n.getCategoria());
        cmp(diff, "Ano",         str(a.getAnoPublicacao()),          str(n.getAnoPublicacao()));
        cmp(diff, "Autores",     a.getAutoresComoTexto(),            n.getAutoresComoTexto());
        cmp(diff, "ISBN",        a.getIsbn(),                       n.getIsbn());
        cmp(diff, "ISSN",        a.getIssn(),                       n.getIssn());
        cmp(diff, "Nº Páginas",  str(a.getNumeroPaginas()),          str(n.getNumeroPaginas()));
        cmp(diff, "Localização", a.getLocalizacao(),                 n.getLocalizacao());
        return String.join(" | ", diff);
    }

    private void cmp(List<String> d, String campo, String a, String b) {
        if (!Objects.equals(a, b)) d.add(campo + ": '" + nvl(a) + "' → '" + nvl(b) + "'");
    }
    private String str(Object o) { return o == null ? "" : o.toString(); }
    private String nvl(String s) { return s == null ? "" : s; }

    private Optional<Usuario> usuarioLogado() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return Optional.empty();
        return usuarioRepo.findByUsername(auth.getName());
    }
}
