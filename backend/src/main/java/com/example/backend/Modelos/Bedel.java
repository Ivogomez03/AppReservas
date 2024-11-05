package com.example.backend.Modelos;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Bedel extends Usuario{

    TurnoDeTrabajo turnoDeTrabajo;
    int idAdminCreador;

}
