package org.example.chatcesur.controller;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductoController {

    private final OllamaChatModel chatModel;

    public ProductoController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/producto/generar-json")
    public String generarDesdeJson(@RequestParam("jsonGuia") String jsonGuia, Model model) {

        model.addAttribute("jsonGuia", jsonGuia);

        if (jsonGuia == null || jsonGuia.isBlank()) {
            model.addAttribute("productoGeneradoJson", "<p>Debes introducir un JSON guía.</p>");
            return "home";
        }

        String prompt = """
                OBJETIVO: Generar un nuevo objeto JSON siguiendo un esquema específico.

                CONTEXTO DE ROL:
                Actúa como un experto en gestión de inventarios para e-commerce.

                DATOS DE REFERENCIA (JSON GUÍA):
                """ + jsonGuia + """

                TAREA:
                Crea un producto NUEVO y DIFERENTE que mantenga exactamente la misma estructura
                de llaves del JSON guía.

                REGLAS CRÍTICAS DE FORMATO Y CONTENIDO:
                1. La categoría debe ser estrictamente "electronica".
                2. No uses null ni dejes campos vacíos.
                3. Genera un EAN válido de 13 dígitos.
                4. Usa una URL de imagen realista.
                5. Los valores de price, rate y count deben ser coherentes.
                6. Devuelve ÚNICAMENTE el bloque JSON.
                7. No uses markdown.
                8. No uses bloques de código.
                9. No añadas explicaciones, saludos ni comentarios.
                """;

        String respuesta = limpiarRespuesta(chatModel.call(prompt));
        model.addAttribute("productoGeneradoJson", formatearComoPre(respuesta));

        return "home";
    }

    @PostMapping("/producto/generar-caracteristicas")
    public String generarDesdeCaracteristicas(@RequestParam("jsonModelo") String jsonModelo,
                                              @RequestParam("caracteristicas") String caracteristicas,
                                              Model model) {

        model.addAttribute("jsonModelo", jsonModelo);
        model.addAttribute("caracteristicas", caracteristicas);

        if (jsonModelo == null || jsonModelo.isBlank()) {
            model.addAttribute("productoGeneradoCaracteristicas", "<p>Debes introducir un JSON modelo.</p>");
            return "home";
        }

        if (caracteristicas == null || caracteristicas.isBlank()) {
            model.addAttribute("productoGeneradoCaracteristicas", "<p>Debes introducir características del producto.</p>");
            return "home";
        }

        String prompt = """
                OBJETIVO: Generar un nuevo objeto JSON a partir de un modelo y de unas características dadas.

                CONTEXTO DE ROL:
                Actúa como un experto en gestión de inventarios para e-commerce.

                JSON MODELO:
                """ + jsonModelo + """

                CARACTERÍSTICAS DEL NUEVO PRODUCTO:
                """ + caracteristicas + """

                TAREA:
                Genera un producto NUEVO usando exactamente la misma estructura de llaves del JSON modelo,
                pero adaptando el contenido a las características proporcionadas.

                REGLAS CRÍTICAS DE FORMATO Y CONTENIDO:
                1. La categoría debe ser estrictamente "electronica".
                2. No uses null ni dejes campos vacíos.
                3. Genera un EAN válido de 13 dígitos.
                4. Usa una URL de imagen realista.
                5. Los valores de price, rate y count deben ser coherentes con el producto descrito.
                6. El resultado debe reflejar claramente las características indicadas.
                7. Devuelve ÚNICAMENTE el bloque JSON.
                8. No uses markdown.
                9. No uses bloques de código.
                10. No añadas explicaciones, saludos ni comentarios.
                """;

        String respuesta = limpiarRespuesta(chatModel.call(prompt));
        model.addAttribute("productoGeneradoCaracteristicas", formatearComoPre(respuesta));

        return "home";
    }

    private String limpiarRespuesta(String texto) {
        if (texto == null) return "";

        return texto
                .replace("```json", "")
                .replace("```JSON", "")
                .replace("```", "")
                .trim();
    }

    private String formatearComoPre(String json) {
        return "<pre><code>" + escaparHtml(json) + "</code></pre>";
    }

    private String escaparHtml(String texto) {
        return texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}