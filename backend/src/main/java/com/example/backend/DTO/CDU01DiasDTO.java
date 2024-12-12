package com.example.backend.DTO;

import java.time.LocalTime;
import com.example.backend.Modelos.DiaSemana;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CDU01DiasDTO {
    DiaSemana dia;
    LocalTime horaInicio;
    int duracion;
}
