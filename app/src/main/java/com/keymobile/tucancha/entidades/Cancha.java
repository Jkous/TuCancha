package com.keymobile.tucancha.entidades;

import java.util.ArrayList;

public class Cancha {

    private String id;
    private String IdPolideportivo;
    private String nombre;
    private int likes;
    private String direccion;
    private double latitude;
    private double longitude;
    private ArrayList<String> caracteristicas;
    private ArrayList<String> tags;
    private ArrayList<Dia> dias;
    private ArrayList<CostoHora> horas;

    @Override
    public String toString() {
        return this.nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPolideportivo() {
        return IdPolideportivo;
    }

    public void setIdPolideportivo(String idPolideportivo) {
        IdPolideportivo = idPolideportivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<String> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(ArrayList<String> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<Dia> getDias() {
        if (dias == null)
            dias = new ArrayList<>();
        return dias;
    }

    public void setDias(ArrayList<Dia> dias) {
        this.dias = dias;
    }

    public ArrayList<CostoHora> getHoras() {
        if(horas == null)
            horas = new ArrayList<>();
        return horas;
    }

    public void setHoras(ArrayList<CostoHora> horas) {
        this.horas = horas;
    }


//
//    public class Horas {
//        private String id;
//        private String IdDia;
//        private int hora;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getIdDia() {
//            return IdDia;
//        }
//
//        public void setIdDia(String idDia) {
//            IdDia = idDia;
//        }
//
//        public int getHora() {
//            return hora;
//        }
//
//        public void setHora(int hora) {
//            this.hora = hora;
//        }
//    }

}
