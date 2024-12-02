package com.example.backend.Servicio;

import com.example.backend.DTO.AulaDTO;
import com.example.backend.Modelos.Aula;

public interface IAulaServicio {
    public Aula crearAula(AulaDTO aulaDTO);
}
