package com.example.backend.Servicio;

import java.util.List;

import com.example.backend.DTO.AulaDTO;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.DTO.ReservaSingularDTO;

public interface IReservaServicio {
    //Registrar una reserva
    public List<AulaDTO> registrarReserva(ReservaDTO reserva);

    //Validar que la reserva no se superponga con otra
    public boolean validarHorasInicioDuracion(ReservaDTO reserva);

    //Validar que la duracion sea multiplo de 30
    public boolean validarDuracionMultiplo30(ReservaDTO reserva);

    //Validar que la fecha ingresada sea posterior a la actual
    public boolean validarFechaActual(ReservaDTO reserva);

    //Validar datos
    public boolean validarDatos(ReservaDTO reserva);   
    
    //Validar nombre
    //public boolean valdiarNombre(String nombre);

    //Validar apellido
    public boolean valdiarApellido(String nombre);

    //Validar correo
    public boolean validarCorreo(String correo);

    //Validar nombre de la catedra
    public boolean validarNombreCatedra(String catedra);

    //Guardar una reserva
    public void guardarReserva(ReservaSingularDTO reserva, AulaDTO aulaDTO);

    //Guardar reserva esporadica
    public void guardarReservaEsporadica(ReservaSingularDTO reserva, AulaDTO aulaDTO);
}
