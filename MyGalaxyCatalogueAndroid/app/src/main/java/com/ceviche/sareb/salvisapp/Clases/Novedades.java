package com.ceviche.sareb.salvisapp.Clases;

public class Novedades {

    String cabecera;
    String cuerpo;


    public Novedades() {
    }


    public Novedades(String cabecera, String cuerpo) {
        this.cabecera = cabecera;
        this.cuerpo = cuerpo;
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
}
