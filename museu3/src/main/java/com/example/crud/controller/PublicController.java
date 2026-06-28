package com.example.crud.controller;

import com.example.crud.model.*;
import com.example.crud.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
public class PublicController {

    private final ObraService    obraService;
    private final NotaService    notaService;
    private final UsuarioService usuarioService;

    public PublicController(ObraService obraService, NotaService notaService, UsuarioService usuarioService) {
        this.obraService = obraService; this.notaService = notaService; this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String home() { return "redirect:/obras"; }

    @GetMapping("/obras")
    public String lista(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) TipoObra tipo,
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            Model model) {

        model.addAttribute("obras",      obraService.buscarComFiltros(busca, tipo, anoInicio, anoFim, dataInicio, dataFim));
        model.addAttribute("tiposObra",  TipoObra.values());
        model.addAttribute("busca",      busca);
        model.addAttribute("tipo",       tipo);
        model.addAttribute("anoInicio",  anoInicio);
        model.addAttribute("anoFim",     anoFim);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim",    dataFim);
        // estatísticas para o hero
        model.addAttribute("totalObras",    obraService.totalObras());
        model.addAttribute("totalLivros",   obraService.totalLivros());
        model.addAttribute("totalRevistas", obraService.totalRevistas());
        model.addAttribute("totalJornais",  obraService.totalJornais());
        return "public/lista";
    }

    @GetMapping("/obras/{id}")
    public String detalhe(@PathVariable Integer id, Model model, Authentication auth) {
        Obra obra = obraService.buscarPorId(id);
        model.addAttribute("obra", obra);
        if (auth != null && auth.isAuthenticated()) {
            model.addAttribute("notas", notaService.listarPorObra(id));
        }
        return "public/detalhe";
    }

    // ── Notas internas (ADMIN ou BIBLIOTECARIO) ──────────

    @PostMapping("/obras/{id}/nota/adicionar")
    public String adicionarNota(@PathVariable Integer id,
                                @RequestParam String conteudo,
                                Authentication auth,
                                RedirectAttributes ra) {
        Obra obra = obraService.buscarPorId(id);
        Usuario autor = usuarioService.buscarPorUsername(auth.getName());
        notaService.adicionar(obra, autor, conteudo);
        ra.addFlashAttribute("sucesso", "Nota adicionada.");
        return "redirect:/obras/" + id;
    }

    @GetMapping("/obras/{id}/nota/{notaId}/excluir")
    public String excluirNota(@PathVariable Integer id, @PathVariable Integer notaId, RedirectAttributes ra) {
        notaService.excluir(notaId);
        ra.addFlashAttribute("sucesso", "Nota removida.");
        return "redirect:/obras/" + id;
    }
}
