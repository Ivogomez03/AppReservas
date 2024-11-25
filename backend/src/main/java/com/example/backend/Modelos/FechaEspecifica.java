package com.example.backend.Modelos;

import java.sql.Date;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class FechaEspecifica {

    private Date fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    @Id
    private int idFechaEspecifica;
    //falta el id del aula
}