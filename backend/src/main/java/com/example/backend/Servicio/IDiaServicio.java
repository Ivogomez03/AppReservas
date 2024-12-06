package com.example.backend.Servicio;

import java.util.List;

import com.example.backend.DTO.CDU01ReservaYAulaFinal;
import com.example.backend.Modelos.Dia;
import com.example.backend.Modelos.Periodica;

public interface IDiaServicio {
    public List<Dia> crearDias(List<CDU01ReservaYAulaFinal> reservaYAula, Periodica periodica);
}
