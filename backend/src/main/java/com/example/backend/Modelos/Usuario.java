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
        this.idUsuario = idUsuario;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.nombre = nombre;
    }

}
