package com.example.backend.Logica;

public abstract class usuario {
    private String Nombre;
    private String Apellido;
    private int ID_usuario;
    private String Contraseña;

    public String getNombre() {
        return Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public int getID_usuario() {
        return ID_usuario;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public void setID_usuario(int iD_usuario) {
        ID_usuario = iD_usuario;
    }
    
    public usuario(String nombre, String apellido, int id_usuario, String contraseña) {
        Nombre = nombre;
        Apellido = apellido;
        ID_usuario = id_usuario;
        Contraseña = contraseña;
    }
}
