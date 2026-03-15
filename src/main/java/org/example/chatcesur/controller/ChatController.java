package org.example.chatcesur.controller;


import org.example.chatcesur.model.PreguntaForm;
import org.example.chatcesur.model.TextoForm;
import org.example.chatcesur.service.ChatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/")
    public String home(Model model) {
        if (!model.containsAttribute("preguntaForm")) {
            model.addAttribute("preguntaForm", new PreguntaForm());
        }

        if (!model.containsAttribute("textoForm")) {
            model.addAttribute("textoForm", new TextoForm());
        }

        return "home";
    }

    @PostMapping("/preguntar")
    public String preguntar(@ModelAttribute PreguntaForm preguntaForm, Model model) {
        String respuesta = chatService.preguntar(preguntaForm.getPregunta());
        preguntaForm.setRespuesta(respuesta);

        model.addAttribute("preguntaForm", preguntaForm);
        model.addAttribute("textoForm", new TextoForm());

        return "home";
    }

    @PostMapping("/procesar")
    public String procesar(@ModelAttribute TextoForm textoForm, Model model) {
        String resultado = chatService.procesarTexto(textoForm.getTexto(), textoForm.getAccion());
        textoForm.setResultado(resultado);

        model.addAttribute("textoForm", textoForm);
        model.addAttribute("preguntaForm", new PreguntaForm());

        return "home";
    }
}