package org.example.chatcesur.service;


import org.example.chatcesur.model.Item;
import org.example.chatcesur.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item crear(Item producto) {

        return itemRepository.save(producto);
    }

    public void deleteById(String id) {
        itemRepository.deleteById(id);
    }

public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(String id) {
        return itemRepository.findById(id);
}

  public  List<Item> findByCategoria(String categoria) {

        return itemRepository.findByCategory(categoria);
  }

  //Listado de items con count < 10
    public List<Item> findByCountLessThan(int count) {
            return itemRepository.findByCountLessThan(count);
        }

  public int actualizarCategoria(String categoriaOrigen, String categoriaDestino) {
        List<Item> items = itemRepository.findByCategory(categoriaOrigen);
        items.forEach(item -> item.setCategory(categoriaDestino));
        itemRepository.saveAll(items);
        return items.size();
    }


    public Item updateCategory(String category, String newCategory) {
        List<Item> items = itemRepository.findByCategory(category);
        items.forEach(item -> item.setCategory(newCategory));
        itemRepository.saveAll(items);
        return null;
    }
}