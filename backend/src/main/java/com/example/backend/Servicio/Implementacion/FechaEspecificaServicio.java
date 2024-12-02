package com.example.backend.Servicio.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.DTO.AulaDTO;
import com.example.backend.DTO.ReservaSingularDTO;
import com.example.backend.Modelos.Aula;
import com.example.backend.Modelos.FechaEspecifica;
import com.example.backend.Repositorio.FechaEspecificaDAO;
import com.example.backend.Servicio.IFechaEspecificaservicio;

public class FechaEspecificaServicio implements IFechaEspecificaservicio {

    @Autowired
    private FechaEspecificaDAO fechaEspecificaDAO;

    @Autowired
    private AulaServicio aulaServicio;

    public FechaEspecifica crearFechaEspecifica(ReservaSingularDTO reserva, AulaDTO aulaDTO) {

        FechaEspecifica fechaEspecifica = new FechaEspecifica();
        fechaEspecifica.setFecha(reserva.getFechaEspecifica().getFecha());
        fechaEspecifica.setHoraInicio(reserva.getFechaEspecifica().getHoraInicio());
        fechaEspecifica.setHoraFin(reserva.getFechaEspecifica().getHoraInicio().plusMinutes(reserva.getPeriodo().getDuracion()));
        fechaEspecifica.setIdFechaEspecifica(reserva.getIdReserva());
        fechaEspecifica.setAula(aula);

        fechaEspecificaDAO.save(fechaEspecifica);
        
        return fechaEspecifica;
    }

}
