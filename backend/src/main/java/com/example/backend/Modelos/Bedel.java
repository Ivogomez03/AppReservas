package com.example.backend.Modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Getter @Setter
@Entity
public class Bedel extends Usuario{

    @Enumerated(EnumType.STRING)
    @Column(name = "turnoDeTrabajo")
    TurnoDeTrabajo turnoDeTrabajo;
    // Relación con la entidad Administrador
    @ManyToOne
    @JoinColumn(name = "idAdminCreador", referencedColumnName = "idUsuario")
    Administrador AdminCreador;


}