package com.example.backend.Modelos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReserva", nullable = false)
    private int idReserva;
    private String nombreProfesor;
    private String apellidoProfesor;
    private String correo; // validar con exprecion regular
    private String nombreCatedra;
    private int idProfesor;
    private int idCatedra;

}
