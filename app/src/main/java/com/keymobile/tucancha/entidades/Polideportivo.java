package com.keymobile.tucancha.entidades;

import java.util.ArrayList;

public class Polideportivo {

    private String id;
    private String nombre;
    private int canchas;
    private int likes;
    private ArrayList<String> listacanchas;

    public Polideportivo() {
    }

    public Polideportivo(String nombre, int canchas, int likes) {
        this.nombre = nombre;
        this.canchas = canchas;
        this.likes = likes;
    }

    public Polideportivo(String id, String nombre, int canchas, int likes) {
        this.id = id;
        this.nombre = nombre;
        this.canchas = canchas;
        this.likes = likes;
    }

    @Override
    public String toString() {
        return nombre;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<String> getListacanchas() {
        return listacanchas;
    }

    public void setListacanchas(ArrayList<String> listacanchas) {
        this.listacanchas = listacanchas;
    }
}
