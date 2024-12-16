package com.example.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CDU01ReservaYAulaFinal {
    private AulaDTO aula;
    private CDU01FechaDTO fechas;
    private CDU01DiasDTO dias;
}
// Ahora cada reserva tiene su aula espacifica