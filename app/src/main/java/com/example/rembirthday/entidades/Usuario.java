package com.example.rembirthday.entidades;

public class Usuario {

    private Integer ano;
    private Integer dia;

    /* renamed from: id */
    private Integer id;
    private String mes;
    private String nombre;

    public Usuario(Integer num, String str, Integer num2, String str2, Integer num3) {
        this.id = num;
        this.nombre = str;
        this.dia = num2;
        this.mes = str2;
        this.ano = num3;
    }

    public Usuario() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String str) {
        this.nombre = str;
    }

    public Integer getDia() {
        return this.dia;
    }

    public void setDia(Integer num) {
        this.dia = num;
    }

    public String getMes() {
        return this.mes;
    }

    public void setMes(String str) {
        this.mes = str;
    }

    public Integer getAno() {
        return this.ano;
    }

    public void setAno(Integer num) {
        this.ano = num;
    }

}
