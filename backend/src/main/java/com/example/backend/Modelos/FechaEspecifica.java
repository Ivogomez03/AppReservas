package com.example.backend.Modelos;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

    @ManyToOne
    @JoinColumn(name = "idReservaEsporadica", nullable = false)
    private Esporadica esporadica;

    @ManyToOne
    @JoinColumn(name = "idAula", nullable = false)
    private Aula aula;
}