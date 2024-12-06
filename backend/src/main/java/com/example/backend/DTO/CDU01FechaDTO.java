package com.example.backend.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CDU01FechaDTO {
    private LocalDate fecha;
    private LocalTime horaInicio;
    private int duracion;
}
