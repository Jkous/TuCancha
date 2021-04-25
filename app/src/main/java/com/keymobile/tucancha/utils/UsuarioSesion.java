package com.keymobile.tucancha.utils;

public class UsuarioSesion {

    private static UsuarioSesion instance;

    private String uid;
    private Boolean is_admin = false;

    public UsuarioSesion() {}

    public static UsuarioSesion configureUsuarioSesion(String uid, boolean is_admin) {
        UsuarioSesion user = getInstance();
        user.uid = uid;
        user.is_admin = is_admin;

        return user;
    }

    public static UsuarioSesion getInstance() {

        if(instance == null)
            instance = new UsuarioSesion();

        return instance;
    }

    public String getUid() {
        return uid;
    }

    public Boolean getIs_admin() {
        return is_admin;
    }
}
