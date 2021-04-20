package com.keymobile.tucancha.entidades;

import java.util.ArrayList;

public class Polideportivo {

    private String id;
    private String nombre;
    private int canchas;
    private double rating;


    public Polideportivo() {
    }

    public Polideportivo(String nombre, int canchas, double rating) {
        this.nombre = nombre;
        this.canchas = canchas;
        this.rating = rating;
    }

    public Polideportivo(String id, String nombre, int canchas, double rating) {
        this.id = id;
        this.nombre = nombre;
        this.canchas = canchas;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCanchas() {
        return canchas;
    }

    public void setCanchas(int canchas) {
        this.canchas = canchas;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
