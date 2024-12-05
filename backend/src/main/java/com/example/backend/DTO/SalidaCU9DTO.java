package com.example.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SalidaCU9DTO {

    private String tipoAula;
    private int numeroDeAula;
    private int capacidad;
    private int piso;
    private boolean habilitado;
    private String error;
    private boolean televisor;
    private boolean computadora;
    private boolean canion;
    private boolean ventilador;
    private int cantidadDeComputadoras;
    private boolean aireAcondicionado;
    private String tipoPizarron;
}
