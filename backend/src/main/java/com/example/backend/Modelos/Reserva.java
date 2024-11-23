package com.example.backend.Modelos;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public abstract class Reserva {

    @Id
    private int idReserva;
    private String nomrbeProfesor;
    private String apellidoProfesor;
    private String correo; // validar con exprecion regular
    private String nombreCatedra; 
    private String idProfesor;
    private String idCatedra;
    
}
