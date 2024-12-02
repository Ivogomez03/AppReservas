package com.example.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ModificarAulaDTO {

    private int numeroDeAula;  
    private int piso;
    private int capacidad;
    private String tipoPizarron;
    private boolean habilitado;
    private boolean canion;
    private boolean aireAcondicionado;
    private boolean ventilador;
    private boolean televisor;
    private boolean proyector;
    private boolean computadora;
    private int cantidadDeComputadoras;
}
