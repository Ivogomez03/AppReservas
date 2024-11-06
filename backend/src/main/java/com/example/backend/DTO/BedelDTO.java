package com.example.backend.DTO;

import com.example.backend.Modelos.TurnoDeTrabajo;

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
    int idAdminCreador;

    public BedelDTO() {
    }

    public BedelDTO(int idUsuario, String nombre, String apellido, String contrasena, TurnoDeTrabajo turnoDeTrabajo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.turnoDeTrabajo = turnoDeTrabajo;
        this.idAdminCreador = 1;
    }
    

}
