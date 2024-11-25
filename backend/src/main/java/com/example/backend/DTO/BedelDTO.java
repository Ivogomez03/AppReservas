package com.example.backend.DTO;

import com.example.backend.Modelos.Administrador;
import com.example.backend.Modelos.TurnoDeTrabajo;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BedelDTO {

    @Id
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String contrasena;
    private TurnoDeTrabajo turnoDeTrabajo;
    private int idAdminCreador;
    private boolean habilitado;
    Administrador AdminCreador;   

}
