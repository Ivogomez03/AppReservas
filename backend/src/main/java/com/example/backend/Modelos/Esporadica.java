package com.example.backend.Modelos;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Esporadica extends Reserva{
@OneToOne(mappedBy = "esporadica", cascade = CascadeType.ALL, orphanRemoval = true)
    private FechaEspecifica fechaEspecifica;
}
