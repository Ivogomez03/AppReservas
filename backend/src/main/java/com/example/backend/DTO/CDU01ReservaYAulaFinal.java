package com.example.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CDU01ReservaYAulaFinal {
    public AulaDTO aula;
    public CDU01FechaDTO fechas;
    public CDU01DiasDTO dias;
}
//Ahora cada reserva tiene su aula espacifica