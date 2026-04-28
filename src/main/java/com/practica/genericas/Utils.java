package com.practica.genericas;

public class Utils {

    public static FechaHora parsearFecha(String fecha, String hora) {
        int dia, mes, anio;
        String[] valores = fecha.split("\\/");
        dia = Integer.parseInt(valores[0]);
        mes = Integer.parseInt(valores[1]);
        anio = Integer.parseInt(valores[2]);
        int minuto, segundo;
        valores = hora.split("\\:");
        minuto = Integer.parseInt(valores[0]);
        segundo = Integer.parseInt(valores[1]);
        FechaHora fechaHora = new FechaHora(dia, mes, anio, minuto, segundo);
        return fechaHora;
    }
}
