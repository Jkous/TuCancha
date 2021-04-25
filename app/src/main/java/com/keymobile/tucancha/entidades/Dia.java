package com.keymobile.tucancha.entidades;

public class Dia {
    private String Abreviatura;
    private boolean Active;

    public Dia() { }

    public Dia(String abreviatura, boolean active) {
        Abreviatura = abreviatura;
        Active = active;
    }

    public String getAbreviatura() {
        return Abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        Abreviatura = abreviatura;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }
}
