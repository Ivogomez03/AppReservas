package com.example.backend.Modelos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Bedel extends Usuario {

    @Enumerated(EnumType.STRING)
    @Column(name = "turnoDeTrabajo")
    private TurnoDeTrabajo turnoDeTrabajo;
    private boolean habilitado;
    // Relación con la entidad Administrador
    @ManyToOne
    @JoinColumn(name = "idAdminCreador", referencedColumnName = "idUsuario")
    private Administrador AdminCreador;

}