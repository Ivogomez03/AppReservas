package com.example.backend.Servicio;

import java.util.List;

import com.example.backend.DTO.CDU01ReservaYAulaFinal;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Modelos.Bedel;

public interface IPeriodicaServicio{
    public void guardarReservaPeriodica(ReservaDTO reservaDTO, List<CDU01ReservaYAulaFinal> reservaYAula, Bedel bedel);
} 