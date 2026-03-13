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
    public String home(@RequestParam(value = "pregunta", required = false) String pregunta, Model model) {
        model.addAttribute("pregunta", pregunta == null ? "" : pregunta);

        if (pregunta != null && !pregunta.isBlank()) {
            String respuesta = chatModel.call(pregunta);
            model.addAttribute("respuesta", respuesta);
        }

        return "home";
    }
}