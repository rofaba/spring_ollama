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

        String preguntaLimpia = normalizarTexto(pregunta);
        model.addAttribute("pregunta", preguntaLimpia);

        if (!preguntaLimpia.isBlank()) {
            String prompt = construirPromptPregunta(preguntaLimpia);
            String respuestaHtml = consultarModelo(prompt);
            model.addAttribute("respuesta", respuestaHtml);
        }

        return "home";
    }

    @GetMapping("/procesar")
    public String procesar(@RequestParam("texto") String texto,
                           @RequestParam("accion") String accion,
                           Model model) {

        String textoLimpio = normalizarTexto(texto);
        String accionLimpia = normalizarTexto(accion);

        model.addAttribute("texto", textoLimpio);
        model.addAttribute("accion", accionLimpia);

        if (!textoLimpio.isBlank()) {
            String prompt = construirPromptProcesamiento(textoLimpio, accionLimpia);
            String resultadoHtml = consultarModelo(prompt);
            model.addAttribute("resultadoTexto", resultadoHtml);
        }

        return "home";
    }

    private String construirPromptPregunta(String pregunta) {
        return """
                Responde en HTML limpio y simple, sin incluir <html>, <head> ni <body>.
                Usa solo etiquetas seguras y visuales como:
                <h3>, <p>, <ul>, <li>, <strong>, <em>, <br>, <code>.

                No uses markdown.
                No uses bloques de código.
                No uses ``` .
                No uses scripts.
                No uses estilos inline.
                La respuesta debe ser clara, breve y bien estructurada.

                Pregunta del usuario:
                """ + pregunta;
    }

    private String construirPromptProcesamiento(String texto, String accion) {
        String instruccion = switch (accion) {
            case "corregir" -> """
                    Corrige el texto en español mejorando ortografía, puntuación y redacción.
                    Devuelve únicamente el texto corregido.
                    """;
            case "resumir" -> """
                    Resume el texto de forma clara y breve.
                    Devuelve únicamente el resumen.
                    """;
            case "traducir" -> """
                    Traduce el texto al inglés con redacción natural.
                    Devuelve únicamente el texto traducido.
                    """;
            default -> """
                    Procesa el texto según la petición.
                    Devuelve únicamente el resultado final.
                    """;
        };

        return """
                Responde SOLO con el resultado final.

                NO añadas títulos.
                NO añadas explicaciones.
                NO añadas frases como "resultado", "traducción" o "english translation".
                NO añadas comillas.
                NO uses markdown.
                NO uses bloques de código.
                NO uses ``` .
                NO incluyas <html>, <head> ni <body>.

                Devuelve únicamente HTML simple y limpio usando solo etiquetas como:
                <p>, <ul>, <li>, <strong>, <em>, <br>.

                """ + instruccion + """

                Texto del usuario:
                """ + texto;
    }

    private String consultarModelo(String prompt) {
        String respuesta = chatModel.call(prompt);
        return limpiarRespuestaHtml(respuesta);
    }

    private String limpiarRespuestaHtml(String respuesta) {
        if (respuesta == null) {
            return "";
        }

        return respuesta
                .replace("```html", "")
                .replace("```HTML", "")
                .replace("```", "")
                .trim();
    }

    private String normalizarTexto(String texto) {
        return texto == null ? "" : texto.trim();
    }
}