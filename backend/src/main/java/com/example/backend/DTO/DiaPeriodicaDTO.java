package com.example.backend.DTO;

import java.time.LocalTime;
import com.example.backend.Modelos.DiaSemana;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class DiaPeriodicaDTO{

    private DiaSemana diaSemana;
    private LocalTime duracion;
    private LocalTime horaInicio;
}
