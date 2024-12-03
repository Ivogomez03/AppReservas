package com.example.backend.Modelos;

import jakarta.persistence.Column;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Dia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDia;

    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    @ManyToOne
    @JoinColumn(name = "idReserva")
    private Periodica periodica;

    @ManyToOne
    @JoinColumn(name = "idAula", nullable = false)
    private Aula aula;
}
