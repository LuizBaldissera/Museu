package com.example.crud.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegal(IllegalArgumentException ex, Model m) {
        m.addAttribute("titulo", "Dados inválidos"); m.addAttribute("mensagem", ex.getMessage()); return "error";
    }
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntime(RuntimeException ex, Model m) {
        m.addAttribute("titulo", "Erro na operação"); m.addAttribute("mensagem", ex.getMessage()); return "error";
    }
    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model m) {
        m.addAttribute("titulo", "Erro inesperado"); m.addAttribute("mensagem", "Ocorreu um erro. Tente novamente."); return "error";
    }
}
