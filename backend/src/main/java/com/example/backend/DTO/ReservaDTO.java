package com.example.backend.DTO;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ReservaDTO {
    
    //Reserva
    private int idReserva;
    private String nombreProfesor;
    private String apellidoProfesor;
    private String correo;
    private String nombreCatedra; 
    private int idProfesor;
    private int idCatedra;
    //Aula
    private int cantidadAlumnos;
    private String tipoAula;
    //Tipo de reserva
    private boolean esporadica;
    private boolean periodicaAnual;
    private boolean periodicaPrimerCuatrimestre;
    private boolean periodicaSegundoCuatrimestre;
    //Dias o fechas para la reserva
    private List<CDU01DiasDTO> dias;
    private List<CDU01FechaDTO> fechasespecificas;
    
}
