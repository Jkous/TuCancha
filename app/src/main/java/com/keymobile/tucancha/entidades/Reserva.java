package com.keymobile.tucancha.entidades;

import java.util.Date;

public class Reserva {

    private String id;
    private String UidUsuario;
    private Date FechaReserva;
    private Date FechaCreacion;
    private String Estado;

    private String IdCancha;

    public Reserva() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUidUsuario() {
        return UidUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        UidUsuario = uidUsuario;
    }

    public Date getFechaReserva() {
        return FechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        FechaReserva = fechaReserva;
    }

    public Date getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getIdCancha() {
        return IdCancha;
    }

    public void setIdCancha(String idCancha) {
        IdCancha = idCancha;
    }
}
