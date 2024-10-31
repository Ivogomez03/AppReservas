package com.example.backend.Logica;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public abstract class usuario {
    private String Nombre;
    private String Apellido;
    @Id 
    private int ID_usuario;
    private String Contraseña;
    
    public usuario() {
    }
    public usuario(String nombre, String apellido, int id_usuario, String contraseña) {
        Nombre = nombre;
        Apellido = apellido;
        ID_usuario = id_usuario;
        Contraseña = contraseña;
    }
}
