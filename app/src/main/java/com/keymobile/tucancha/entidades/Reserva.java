package com.keymobile.tucancha.entidades;

import java.util.Date;

public class Reserva {

    private String id;
    private String UidUsuario;
    private Date FechaReserva;
    private int HoraReserva;
    private double PrecioReserva;
    private Date FechaCreacion;
    private String Estado;

    private String IdCancha;

    private Cancha DataCancha;

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

    public int getHoraReserva() {
        return HoraReserva;
    }

    public void setHoraReserva(int horaReserva) {
        HoraReserva = horaReserva;
    }

    public double getPrecioReserva() {
        return PrecioReserva;
    }

    public void setPrecioReserva(double precioReserva) {
        PrecioReserva = precioReserva;
    }

    public Cancha getDataCancha() {
        return DataCancha;
    }

    public void setDataCancha(Cancha dataCancha) {
        DataCancha = dataCancha;
    }

    @Override
    public String toString() {
        return "Reserva = " + HoraReserva ;
    }
}
