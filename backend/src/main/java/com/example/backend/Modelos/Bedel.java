package com.example.backend.Modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Getter @Setter
@Entity
public class Bedel extends Usuario{

    @Enumerated(EnumType.STRING)
    @Column(name = "turnoDeTrabajo")
    TurnoDeTrabajo turnoDeTrabajo;
    @Column(name = "idAdminCreador")
    Administrador AdminCreador;

}
