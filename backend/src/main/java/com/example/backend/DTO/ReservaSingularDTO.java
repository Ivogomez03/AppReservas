package com.example.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReservaSingularDTO {
    private int idReserva;
    private String nombreProfesor;
    private String apellidoProfesor;
    private String correo; // validar con exprecion regular
    private String nombreCatedra; 
    private String idProfesor;
    private String idCatedra;
    private boolean esporadica;
    private boolean periodicaAnual;
    private boolean periodicaPrimerCuatrimestre;
    private boolean periodicaSegundoCuatrimestre;
    private CDU01DiasDTO dias;
    private CDU01FechaDTO fechaEspecifica;
}
