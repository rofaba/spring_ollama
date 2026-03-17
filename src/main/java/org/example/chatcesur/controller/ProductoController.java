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

    @PostMapping("/producto/generar")
    public String generarProducto(@RequestParam("jsonGuia") String jsonGuia, Model model) {

        model.addAttribute("jsonGuia", jsonGuia);

        if (jsonGuia == null || jsonGuia.isBlank()) {
            model.addAttribute("productoGenerado", "<p>Debes introducir un JSON guía.</p>");
            return "home";
        }

        String prompt = """
                OBJETIVO: Generar un nuevo objeto JSON siguiendo un esquema específico.

                CONTEXTO DE ROL:
                Actúa como un experto en gestión de inventarios para e-commerce.

                DATOS DE REFERENCIA (JSON GUÍA):
                """ + jsonGuia + """

                TAREA:
                Crea un producto NUEVO y DIFERENTE que mantenga la misma estructura de llaves (keys)
                y la misma categoría mencionada arriba.

                REGLAS CRÍTICAS DE FORMATO Y CONTENIDO:
                1. CATEGORÍA: El nuevo producto debe ser estrictamente de la categoría "electronica".
                2. COMPLETITUD: Prohibido usar valores null o dejar campos vacíos.
                3. EAN: Inventa un código EAN válido de 13 dígitos.
                4. IMAGEN: Usa una URL de imagen realista.
                5. REALISMO: Los valores de price, rate y count deben ser coherentes.
                6. SALIDA LIMPIA: Devuelve ÚNICAMENTE el bloque JSON.
                7. NO uses markdown.
                8. NO uses bloques de código.
                9. NO añadas explicaciones antes ni después.

                RESULTADO ESPERADO:
                Devuelve solo el JSON.
                """;

        String respuesta = chatModel.call(prompt);

        String limpio = respuesta
                .replace("```json", "")
                .replace("```JSON", "")
                .replace("```", "")
                .trim();

        model.addAttribute("productoGenerado", formatearComoPre(limpio));

        return "home";
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