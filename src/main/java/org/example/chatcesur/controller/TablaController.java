package org.example.chatcesur.controller;

import org.example.chatcesur.service.TablaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TablaController {

    private final TablaService tablaService;

    public TablaController(TablaService tablaService) {
        this.tablaService = tablaService;
    }

    @GetMapping("/tabla")
    public String tabla(Model model) {
        model.addAttribute("numeroTabla", "");
        return "home";
    }

    @PostMapping("/tabla/generar")
    public String generarTabla(@RequestParam("numero") int numero, Model model) {
        String resultado = tablaService.generarTabla(numero);

        model.addAttribute("tablaResultado", resultado);
        model.addAttribute("numeroTabla", numero);

        return "home";
    }
}