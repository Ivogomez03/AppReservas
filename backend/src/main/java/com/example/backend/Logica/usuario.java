package com.example.backend.Logica;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public abstract class usuario {
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Id 
    @Column(name = "idUsuario")
    private int idUsuario;
    @Column(name = "contrasena")
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
