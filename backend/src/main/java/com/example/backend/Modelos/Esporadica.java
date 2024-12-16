package com.example.backend.Modelos;

import java.util.List;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Esporadica extends Reserva {

    @OneToMany(mappedBy = "esporadica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FechaEspecifica> fechaEspecifica;

    @ManyToOne
    @JoinColumn(name = "idBedel", nullable = true, referencedColumnName = "idUsuario")
    private Bedel bedel;
}
