package com.example.rembirthday.utilidades;

public class Utilidades {

    public static String CAMPO_ANO = "ano";
    public static String CAMPO_DIA = "dia";
    public static String CAMPO_ID = "id";
    public static String CAMPO_MES = "mes";
    public static String CAMPO_NOMBRE = "nombre";
    public static final String CREAR_TABLA_USUARIO;
    public static String TABLA_USUARIO = "usuario";

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(TABLA_USUARIO);
        sb.append(" (");
        sb.append(CAMPO_ID);
        sb.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(CAMPO_NOMBRE);
        sb.append(" TEXT, ");
        sb.append(CAMPO_DIA);
        sb.append(" INTEGER, ");
        sb.append(CAMPO_MES);
        sb.append(" TEXT, ");
        sb.append(CAMPO_ANO);
        sb.append(" INTEGER)");
        CREAR_TABLA_USUARIO = sb.toString();
    }

}
