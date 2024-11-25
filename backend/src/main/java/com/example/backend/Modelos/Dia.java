package com.example.backend.Modelos;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Dia {

    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    @Id
    private int idDia;
    @ManyToOne
    private Periodica periodica;
    private Aula aula;
}
