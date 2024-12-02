package com.example.backend.Modelos;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class FechaEspecifica {

      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFechaEspecifica;

    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(nullable = false)
    private LocalTime HoraInicio;
    
    @Column(nullable = false)
    private LocalTime HoraFin;
    @ManyToOne
    @JoinColumn(name = "idReservaEsporadica", nullable = false)
    private Esporadica esporadica;

    @ManyToOne
    @JoinColumn(name = "idAula", nullable = false)
    private Aula aula;
}