package com.example.backend.Modelos;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Periodo {
    @Id
    private int idPeriodo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private TipoPeriodo tipoPeriodo;
}
