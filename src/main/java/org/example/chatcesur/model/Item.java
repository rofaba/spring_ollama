package org.example.chatcesur.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    private String id;

    @NotBlank(message = "El nombre es obligatorio")
    private String title;

    @NotNull
    private Double price;

    @NotBlank
    private String category;

    @NotBlank
    private String description;

    @NotNull
    private Double rate;

    private int count;

    @NotBlank
    private String color;

    @NotBlank
    private String manufacturer;

    @NotBlank
    private String ean;

    @NotBlank
    private String image;
}