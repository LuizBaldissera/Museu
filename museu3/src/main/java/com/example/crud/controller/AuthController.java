package com.example.crud.controller;

import com.example.crud.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) { this.usuarioService = usuarioService; }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String erro, Model model) {
        if (erro != null) model.addAttribute("erro", "Usuário ou senha incorretos.");
        return "auth/login";
    }

    /** Exibe formulário de cadastro de novo usuário. */
    @GetMapping("/registro")
    public String registroForm(Model model) {
        return "auth/registro";
    }

    /**
     * Processa o cadastro.
     * Novos usuários sempre recebem perfil BIBLIOTECARIO.
     * Para perfil ADMIN, o administrador promove manualmente.
     */
    @PostMapping("/registro")
    public String registrar(
            @RequestParam String username,
            @RequestParam String senha,
            @RequestParam String confirmarSenha,
            @RequestParam String nomeCompleto,
            RedirectAttributes ra) {

        if (!senha.equals(confirmarSenha)) {
            ra.addFlashAttribute("erro", "As senhas não coincidem.");
            return "redirect:/registro";
        }
        try {
            usuarioService.registrar(username, senha, nomeCompleto);
            ra.addFlashAttribute("sucesso",
                    "Conta criada com sucesso! Faça login com o usuário '" + username.trim().toLowerCase() + "'.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:/registro";
        }
    }

    @GetMapping("/acesso-negado")
    public String acessoNegado() { return "error"; }
}
