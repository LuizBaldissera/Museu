package com.example.crud.controller;

import com.example.crud.model.*;
import com.example.crud.service.ObraService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
@RequestMapping("/admin/obras")
public class ObraController {

    private final ObraService obraService;

    public ObraController(ObraService obraService) { this.obraService = obraService; }

    /** Lista obras com filtros opcionais (Req. 1 + novo requisito de filtro admin). */
    @GetMapping
    public String lista(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) TipoObra tipo,
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            Model model) {

        boolean temFiltro = busca != null || tipo != null || anoInicio != null
                            || anoFim != null || dataInicio != null || dataFim != null;

        var obras = temFiltro
                ? obraService.buscarComFiltros(busca, tipo, anoInicio, anoFim, dataInicio, dataFim)
                : obraService.listarTodos();

        model.addAttribute("obras",      obras);
        model.addAttribute("tiposObra",  TipoObra.values());
        model.addAttribute("busca",      busca);
        model.addAttribute("tipo",       tipo);
        model.addAttribute("anoInicio",  anoInicio);
        model.addAttribute("anoFim",     anoFim);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim",    dataFim);
        model.addAttribute("temFiltro",  temFiltro);
        return "admin/obra-lista";
    }

    @GetMapping("/nova")
    public String formularioNovo(Model model) {
        model.addAttribute("obra", new Obra());
        model.addAttribute("tiposObra", TipoObra.values());
        model.addAttribute("tituloPagina", "Nova Obra");
        return "admin/obra-form";
    }

    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable Integer id, Model model) {
        Obra obra = obraService.buscarPorId(id);
        model.addAttribute("obra", obra);
        model.addAttribute("autoresTexto", obra.getAutoresComoTexto());
        model.addAttribute("tiposObra", TipoObra.values());
        model.addAttribute("tituloPagina", "Editar Obra");
        return "admin/obra-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Obra obra,
                         @RequestParam(required = false) String autoresTexto,
                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAquisicao,
                         RedirectAttributes ra) {
        obra.setDataAquisicao(dataAquisicao);
        obraService.salvar(obra, autoresTexto);
        ra.addFlashAttribute("sucesso", obra.getId() == null ? "Obra cadastrada!" : "Obra atualizada!");
        return "redirect:/admin/obras";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Integer id, RedirectAttributes ra) {
        obraService.deletar(id);
        ra.addFlashAttribute("sucesso", "Obra excluída com sucesso.");
        return "redirect:/admin/obras";
    }
}
