package org.example.chatcesur.service;


import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class OllamaChatService implements ChatService {

    private final OllamaChatModel chatModel;

    public OllamaChatService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String preguntar(String pregunta) {
        if (pregunta == null || pregunta.isBlank()) {
            return "Debes escribir una pregunta.";
        }

        try {
            return chatModel.call(pregunta);
        } catch (Exception e) {
            return "Error al contactar con la IA.";
        }
    }

    @Override
    public String procesarTexto(String texto, String accion) {
        if (texto == null || texto.isBlank()) {
            return "Debes escribir un texto.";
        }

        String prompt;

        switch (accion) {
            case "resumir":
                prompt = "Resume el siguiente texto: " + texto;
                break;
            case "traducir":
                prompt = "Traduce al inglés el siguiente texto: " + texto;
                break;
            case "corregir":
                prompt = "Corrige y mejora la redacción del siguiente texto: " + texto;
                break;
            default:
                prompt = texto;
        }

        try {
            return chatModel.call(prompt);
        } catch (Exception e) {
            return "Error al contactar con la IA.";
        }
    }
}