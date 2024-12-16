package com.example.backend.DTO;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CDU01ReservasYAulas {
    private List<AulaDTO> aulas;
    private CDU01FechaDTO fechas;
    private CDU01DiasDTO dias;
}
// se asocia a cada reserva sin mirar su tipo una lista de aulas que la
// satisfaga