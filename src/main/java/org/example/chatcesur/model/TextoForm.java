package org.example.chatcesur.model;



public class TextoForm {

    private String texto;
    private String accion;
    private String resultado;

    public TextoForm() {
        this.texto = "";
        this.accion = "resumir";
        this.resultado = "";
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

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}