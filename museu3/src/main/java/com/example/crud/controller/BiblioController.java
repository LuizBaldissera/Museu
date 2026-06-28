package com.example.crud.controller;

import com.example.crud.model.Usuario;
import com.example.crud.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Painel exclusivo para o perfil BIBLIOTECARIO.
 * ADMIN também pode acessar (útil para visualizar como bibliotecário).
 */
@Controller
@RequestMapping("/biblio")
public class BiblioController {

    private final ObraService    obraService;
    private final NotaService    notaService;
    private final UsuarioService usuarioService;

    public BiblioController(ObraService obraService, NotaService notaService, UsuarioService usuarioService) {
        this.obraService = obraService; this.notaService = notaService; this.usuarioService = usuarioService;
    }

    @GetMapping("/painel")
    public String painel(Model model, Authentication auth) {
        Usuario usuario = usuarioService.buscarPorUsername(auth.getName());
        var minhasNotas = notaService.listarPorUsuario(auth.getName());

        model.addAttribute("usuario",    usuario);
        model.addAttribute("minhasNotas", minhasNotas);
        model.addAttribute("totalObras",  obraService.totalObras());
        model.addAttribute("totalNotas",  minhasNotas.size());
        return "biblio/painel";
    }
}
