package com.example.backend.Servicio;

import java.util.List;

import com.example.backend.DTO.CDU01ReservaYAulaFinal;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Modelos.Bedel;

public interface IEsporadicaServicio {
    public void guardarReservaEsporadica(List<CDU01ReservaYAulaFinal> reservaYAula, ReservaDTO reservaDTO, Bedel bedel);
}
