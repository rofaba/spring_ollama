package org.example.chatcesur.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chatcesur.model.Item;
import org.example.chatcesur.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ItemViewController {

    private final ItemService itemService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ItemViewController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item/mongo")
    public String buscarItemMongo(@RequestParam("id") String id, Model model) {
        model.addAttribute("mongoId", id);

        Optional<Item> itemOpt = itemService.findById(id);

        if (itemOpt.isEmpty()) {
            model.addAttribute("itemMongoJson", "<p>Producto no encontrado.</p>");
            return "home";
        }

        try {
            String json = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(itemOpt.get());

            model.addAttribute("itemMongoJson", "<pre><code>" + escaparHtml(json) + "</code></pre>");
        } catch (JsonProcessingException e) {
            model.addAttribute("itemMongoJson", "<p>Error al convertir el producto a JSON.</p>");
        }

        return "home";
    }

    private String escaparHtml(String texto) {
        return texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}