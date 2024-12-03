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
    private String nombreProfesor;
    private String apellidoProfesor;
    private String correo; // validar con exprecion regular
    private String nombreCatedra; 
    private int idProfesor;
    private int idCatedra;
    
}
