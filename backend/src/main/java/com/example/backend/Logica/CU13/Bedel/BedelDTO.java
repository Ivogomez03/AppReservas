package com.example.backend.Logica.CU13.Bedel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public class BedelDTO {
    private String nombre;
    private String apellido;
    private int idUsuario;
    private String contrasena;
    private TurnoDeTrabajo turnoDeTrabajo;
    private int ID_admin_creador;

    public BedelDTO() { }

    public BedelDTO(String nombre, String apellido, int idUsuario, TurnoDeTrabajo turnoDeTrabajo, int ID_admin_creador, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.idUsuario = idUsuario;
        this.contrasena = contrasena;
        this.turnoDeTrabajo = turnoDeTrabajo;
        this.ID_admin_creador = ID_admin_creador;
    }

    
}

