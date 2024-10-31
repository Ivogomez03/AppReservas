package com.example.backend.Logica;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public abstract class usuario {
    private String nombre;
    private String apellido;
    @Id 
    private int idUsuario;
    private String contrasena;
    
    public usuario() {
    }
    public usuario(String nombre, String apellido, int idUsuario, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.idUsuario = idUsuario;
        this.contrasena = contrasena;
    }
}
