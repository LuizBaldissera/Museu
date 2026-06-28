package com.example.crud.config;

import com.example.crud.service.VisitaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VisitaInterceptor implements HandlerInterceptor {

    private final VisitaService visitaService;
    private final Set<String> sessoesContadas = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private LocalDate diaAtual = LocalDate.now();

    public VisitaInterceptor(VisitaService visitaService) { this.visitaService = visitaService; }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        String uri = req.getRequestURI();
        if (uri.startsWith("/admin") || uri.startsWith("/css") || uri.startsWith("/js")
                || uri.startsWith("/images") || uri.equals("/login") || uri.equals("/logout")
                || uri.startsWith("/biblio") || uri.endsWith(".ico") || uri.endsWith(".svg")) {
            return true;
        }
        LocalDate hoje = LocalDate.now();
        if (!hoje.equals(diaAtual)) { sessoesContadas.clear(); diaAtual = hoje; }
        if (sessoesContadas.add(req.getSession(true).getId())) visitaService.registrarVisita();
        return true;
    }
}
