package org.example.chatcesur.controller;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {

    private final OllamaChatModel chatModel;

    public ChatController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/")
    public String home(@RequestParam(value = "pregunta", required = false) String pregunta,
                       Model model) {

        model.addAttribute("pregunta", pregunta == null ? "" : pregunta);

        if (pregunta != null && !pregunta.isBlank()) {

            String prompt = """
                    Responde en HTML limpio y simple, sin incluir <html>, <head> ni <body>.
                    Usa solo etiquetas seguras y visuales como:
                    <h3>, <p>, <ul>, <li>, <strong>, <em>, <br>, <code>.
                    
                    No uses markdown.
                    No uses scripts.
                    No uses estilos inline.
                    La respuesta debe ser clara, breve y bien estructurada.
                    
                    Pregunta del usuario:
                    """ + pregunta;

            String respuestaHtml = chatModel.call(prompt);

            model.addAttribute("respuesta", respuestaHtml);
        }

        return "home";
    }
}