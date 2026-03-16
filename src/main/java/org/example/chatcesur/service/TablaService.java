package org.example.chatcesur.service;

import org.springframework.stereotype.Service;

@Service
public class TablaService {

    public String generarTabla(int numero) {

        StringBuilder html = new StringBuilder();

        html.append("<table class='tabla'>");
        html.append("<tr><th>Operación</th><th>Resultado</th></tr>");

        for (int i = 1; i <= 10; i++) {

            html.append("<tr>");
            html.append("<td>").append(numero).append(" × ").append(i).append("</td>");
            html.append("<td>").append(numero * i).append("</td>");
            html.append("</tr>");

        }

        html.append("</table>");

        return html.toString();
    }
}