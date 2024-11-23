package com.example.backend.Modelos;

import java.sql.Date;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Periodo {
    @Id
    private int idPeriodo;
    private Date fechaInicio;
    private Date fechaFin;
}
