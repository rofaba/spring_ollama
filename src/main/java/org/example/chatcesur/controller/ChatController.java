package org.example.chatcesur.controller;

import org.example.chatcesur.service.ChatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/")
    public String home(@RequestParam(value = "pregunta", required = false) String pregunta,
                       Model model) {

        String preguntaLimpia = pregunta == null ? "" : pregunta.trim();
        model.addAttribute("pregunta", preguntaLimpia);

        if (!preguntaLimpia.isBlank()) {
            String respuesta = chatService.preguntar(preguntaLimpia);
            model.addAttribute("respuesta", respuesta);
        }

        return "home";
    }

    @GetMapping("/procesar")
    public String procesar(@RequestParam("texto") String texto,
                           @RequestParam("accion") String accion,
                           Model model) {

        String textoLimpio = texto == null ? "" : texto.trim();
        String accionLimpia = accion == null ? "" : accion.trim();

        model.addAttribute("texto", textoLimpio);
        model.addAttribute("accion", accionLimpia);

        if (!textoLimpio.isBlank()) {
            String resultado = chatService.procesarTexto(textoLimpio, accionLimpia);
            model.addAttribute("resultadoTexto", resultado);
        }

        return "home";
    }
}