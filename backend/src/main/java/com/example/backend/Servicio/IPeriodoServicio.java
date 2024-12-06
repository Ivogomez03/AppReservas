package com.example.backend.Servicio;

import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Modelos.Periodo;

public interface IPeriodoServicio {
    public Periodo obtenerPeriodo(ReservaDTO reserva);
}
