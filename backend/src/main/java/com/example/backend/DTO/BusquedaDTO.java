package com.example.backend.DTO;

import com.example.backend.Modelos.TurnoDeTrabajo;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BusquedaDTO {
    String nombre;
    TurnoDeTrabajo turnoDeTrabajo;
}
