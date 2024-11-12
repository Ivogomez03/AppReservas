package com.example.backend.Modelos;

import jakarta.persistence.Entity;

@Entity
public class Administrador extends Usuario {

    public Administrador() {
        super();
    }
    public Administrador(String nombre, String apellido, int idUsuario, String contrasena) {
        super(nombre, apellido, idUsuario, contrasena);
    }
}