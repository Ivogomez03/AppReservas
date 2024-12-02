package com.example.backend.Servicio;

import com.example.backend.DTO.ReservaSingularDTO;
import com.example.backend.Modelos.FechaEspecifica;
import com.example.backend.DTO.AulaDTO;

public interface IFechaEspecificaservicio {
    public FechaEspecifica crearFechaEspecifica(ReservaSingularDTO reserva, AulaDTO aulaDTO);
}
