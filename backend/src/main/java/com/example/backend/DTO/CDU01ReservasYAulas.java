package com.example.backend.DTO;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CDU01ReservasYAulas {
    public List<AulaDTO> aulas;
    public CDU01FechaDTO fechas;
    public CDU01DiasDTO dias;
}
//se asocia a cada reserva sin mirar su tipo una lista de aulas que la satisfaga