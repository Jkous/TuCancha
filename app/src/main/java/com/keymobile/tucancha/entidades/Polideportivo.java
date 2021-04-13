package com.keymobile.tucancha.entidades;

public class Polideportivo {

    private int id;
    private String nombre;
    private int canchas;
    private double rating;

    public Polideportivo(String nombre, int canchas, double rating) {
        this.nombre = nombre;
        this.canchas = canchas;
        this.rating = rating;
    }

    public Polideportivo(int id, String nombre, int canchas, double rating) {
        this.id = id;
        this.nombre = nombre;
        this.canchas = canchas;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
