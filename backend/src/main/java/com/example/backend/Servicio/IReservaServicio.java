package com.example.backend.Servicio;

import java.util.List;

import com.example.backend.DTO.CDU01ReservaYAulaFinal;
import com.example.backend.DTO.CDU01ReservasYAulas;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Excepciones.ValidationException;

public interface IReservaServicio {
    //Registrar una reserva
    public List<CDU01ReservasYAulas> registrarReserva(ReservaDTO reserva) throws ValidationException, ClassNotFoundException;

    //Validar que la reserva no se superponga con otra
    public boolean validarHorasInicioDuracion(ReservaDTO reserva);

    //Validar que la duracion sea multiplo de 30
    public boolean validarDuracionMultiplo30(ReservaDTO reserva);

    //Validar que la fecha ingresada sea posterior a la actual
    public boolean validarFechaActual(ReservaDTO reserva);

    //Validar datos
    public boolean validarDatos(ReservaDTO reserva);   
    
    //Validar nombre
    public boolean validarNombre(String nombre);

    //Validar apellido
    public boolean valdiarApellido(String nombre);

    //Validar correo
    public boolean validarCorreo(String correo);

    //Validar nombre de la catedra
    public boolean validarNombreCatedra(String catedra);

    //Guardar una reserva
    public void guardarReserva(List<CDU01ReservaYAulaFinal> reservaYAula, ReservaDTO reserva);

    public List<CDU01ReservasYAulas> obtenerAulas(ReservaDTO reserva) throws ClassNotFoundException;
}
