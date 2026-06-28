package com.example.crud.controller;

import com.example.crud.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ObraService   obraService;
    private final LogService    logService;
    private final VisitaService visitaService;

    public AdminController(ObraService obraService, LogService logService, VisitaService visitaService) {
        this.obraService = obraService; this.logService = logService; this.visitaService = visitaService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalObras",    obraService.totalObras());
        model.addAttribute("totalLivros",   obraService.totalLivros());
        model.addAttribute("totalRevistas", obraService.totalRevistas());
        model.addAttribute("totalJornais",  obraService.totalJornais());
        model.addAttribute("visitasHoje",   visitaService.visitasHoje());
        model.addAttribute("totalVisitas",  visitaService.totalHistorico());
        model.addAttribute("ultimosLogs",   logService.getUltimos(10));
        return "admin/dashboard";
    }

    @GetMapping("/logs")
    public String logs(Model model) {
        model.addAttribute("logs", logService.getTodos());
        return "admin/logs";
    }
}
