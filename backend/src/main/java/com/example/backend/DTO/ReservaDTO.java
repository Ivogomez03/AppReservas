package com.example.backend.DTO;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaDTO {
    private int idReserva;
    private String nombreProfesor;
    private String apellidoProfesor;
    private String correo;
    private String nombreCatedra;
    private int idProfesor;
    private int idCatedra;
    private int cantidadAlumnos;
    private String tipoAula;
    private boolean esporadica;
    private boolean periodicaAnual;
    private boolean periodicaPrimerCuatrimestre;
    private boolean periodicaSegundoCuatrimestre;
    private List<CDU01DiasDTO> dias;
    private List<CDU01FechaDTO> fechasespecificas;
    private int idBedel; // Add this field
}
