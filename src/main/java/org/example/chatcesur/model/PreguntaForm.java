package org.example.chatcesur.model;



public class PreguntaForm {

    private String pregunta;
    private String respuesta;

    public PreguntaForm() {
        this.pregunta = "";
        this.respuesta = "";
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}