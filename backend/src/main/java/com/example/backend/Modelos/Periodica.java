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

 @OneToMany(mappedBy = "periodica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dia> dias;

    @ManyToOne
    @JoinColumn(name = "idPeriodo", nullable = false)
    private Periodo periodo;
}