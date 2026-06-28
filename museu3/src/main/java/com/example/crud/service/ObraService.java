package com.example.crud.service;

import com.example.crud.model.*;
import com.example.crud.repository.AutorRepository;
import com.example.crud.repository.ObraRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class ObraService {

    private final ObraRepository  obraRepo;
    private final AutorRepository autorRepo;
    private final LogService      logService;

    public ObraService(ObraRepository obraRepo, AutorRepository autorRepo, LogService logService) {
        this.obraRepo = obraRepo; this.autorRepo = autorRepo; this.logService = logService;
    }

    public List<Obra> listarTodos() { return obraRepo.findAll(Sort.by("titulo")); }

    public Obra buscarPorId(Integer id) {
        return obraRepo.findById(id).orElseThrow(() -> new RuntimeException("Obra " + id + " não encontrada."));
    }

    public List<Obra> buscarComFiltros(String busca, TipoObra tipo,
                                       Integer anoInicio, Integer anoFim,
                                       LocalDate dataInicio, LocalDate dataFim) {
        String b = (busca != null && busca.isBlank()) ? null : busca;
        return obraRepo.buscarComFiltros(b, tipo, anoInicio, anoFim, dataInicio, dataFim);
    }

    public long totalObras()    { return obraRepo.count(); }
    public long totalLivros()   { return obraRepo.countByTipoObra(TipoObra.LIVRO); }
    public long totalRevistas() { return obraRepo.countByTipoObra(TipoObra.REVISTA); }
    public long totalJornais()  { return obraRepo.countByTipoObra(TipoObra.JORNAL); }

    @Transactional
    public void salvar(Obra obra, String autoresTexto) {
        boolean isNova = (obra.getId() == null);
        Obra antiga = null;
        if (!isNova) antiga = clonar(buscarPorId(obra.getId()));
        obra.setAutores(resolverAutores(autoresTexto));
        obraRepo.save(obra);
        if (isNova) logService.registrarCriacao(obra);
        else        logService.registrarEdicao(antiga, obra);
    }

    @Transactional
    public void deletar(Integer id) {
        Obra obra = buscarPorId(id);
        logService.registrarExclusao(obra);
        obra.getAutores().clear();
        obraRepo.delete(obra);
    }

    private List<Autor> resolverAutores(String texto) {
        if (texto == null || texto.isBlank()) return new ArrayList<>();
        List<Autor> lista = new ArrayList<>();
        for (String n : texto.split(",")) {
            String nome = n.trim();
            if (nome.isEmpty()) continue;
            lista.add(autorRepo.findByNomeIgnoreCase(nome).orElseGet(() -> autorRepo.save(new Autor(nome))));
        }
        return lista;
    }

    private Obra clonar(Obra o) {
        Obra c = new Obra();
        c.setId(o.getId()); c.setTitulo(o.getTitulo());
        c.setAutores(new ArrayList<>(o.getAutores()));
        c.setTipoObra(o.getTipoObra()); c.setCategoria(o.getCategoria());
        c.setAnoPublicacao(o.getAnoPublicacao()); c.setDataAquisicao(o.getDataAquisicao());
        c.setIsbn(o.getIsbn()); c.setIssn(o.getIssn()); c.setNumeroPaginas(o.getNumeroPaginas());
        c.setTecnica(o.getTecnica()); c.setDimensoes(o.getDimensoes()); c.setLocalizacao(o.getLocalizacao());
        c.setDescricao(o.getDescricao());
        return c;
    }
}
