package com.example.backend.Modelos;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public abstract class Usuario {

    @Id
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String contrasena;
    
    public Usuario() {
    }
    public Usuario(String nombre, String apellido, int idUsuario, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.idUsuario = idUsuario;
        this.contrasena = contrasena;
    }
}
