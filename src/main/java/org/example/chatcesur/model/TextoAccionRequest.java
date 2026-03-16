package org.example.chatcesur.model;

public class TextoAccionRequest {

    private String texto;
    private String accion;

    public TextoAccionRequest() {
    }

    public TextoAccionRequest(String texto, String accion) {
        this.texto = texto;
        this.accion = accion;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
}