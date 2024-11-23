package com.example.backend.Modelos;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Aula {

    private String tipoPizarron;
    private int numeroDeAula;
    private int capacidad;
    private int piso;
    @Id
    private int idAula;
    private String caracteristicas;
    private boolean aireAcondicionado;
    private boolean habilitado;

    public Aula() {
    }
    public Aula(String tipoPizarron, int numeroDeAula, int capacidad, int piso, int idAula, String caracteristicas, boolean aireAcondicionado, boolean habilitado) {
        this.tipoPizarron = tipoPizarron;
        this.numeroDeAula = numeroDeAula;
        this.capacidad = capacidad;
        this.piso = piso;
        this.idAula = idAula;
        this.caracteristicas = caracteristicas;
        this.aireAcondicionado = aireAcondicionado;
        this.habilitado = true;
    }

    public boolean setCapacidad(int capacidad) {
        if (capacidad > 0) {
            this.capacidad = capacidad;
            return true;
        }
        return false;
    }
}
