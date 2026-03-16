package org.example.chatcesur.service;


public interface ChatService {

    String preguntar(String pregunta);

    String procesarTexto(String texto, String accion);
}