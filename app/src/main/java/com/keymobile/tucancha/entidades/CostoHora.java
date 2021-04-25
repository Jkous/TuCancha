package com.keymobile.tucancha.entidades;

import java.util.Objects;

public class CostoHora {
    private String id;
    private int hora;
    private double precio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CostoHora costoHora = (CostoHora) o;
        return id.equals(costoHora.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
