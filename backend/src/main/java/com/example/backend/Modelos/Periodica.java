package com.example.backend.Modelos;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Periodica extends Reserva{

    private Date fechaInicio;
    private Date fechaFin;
    //periodo es el que tiene la fecha de inicio y la de fin
    @ManyToOne
    private Periodo periodo;
    @OneToMany
    private List<Dia> dias;
}