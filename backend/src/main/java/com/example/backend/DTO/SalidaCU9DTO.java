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
}
