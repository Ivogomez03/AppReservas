package com.example.backend.DTO;

import TpDiseno.Backend.Modelos.TurnoDeTrabajo;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BedelDTO {

    @Id
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String contrasena;
    private TurnoDeTrabajo turnoDeTrabajo;

    public BedelDTO() {
    }

    public BedelDTO(int idUsuario, String nombre, String apellido, String contrasena, TurnoDetrabajo turnoDeTrabajo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.turnoDeTrabajo = turnoDeTrabajo;
    }
    

}
