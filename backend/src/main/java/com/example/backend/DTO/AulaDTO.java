package com.example.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AulaDTO {

    private String tipoPizarron;
    private int numeroDeAula;
    private int capacidad;
    private int piso;
    private int idAula;
    private String caracteristicas;
    private boolean aireAcondicionado;
    private boolean habilitado;
}
