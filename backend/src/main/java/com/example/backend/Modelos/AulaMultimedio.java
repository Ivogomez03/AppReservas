package com.example.backend.Modelos;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class AulaMultimedio extends Aula{

    private boolean televisor;
    private boolean proyector;
    private boolean computadora;
    private boolean ventilador;

    public AulaMultimedio() {
    }
    public AulaMultimedio(boolean televisor, boolean proyector, boolean computadora, boolean ventilador, String tipoPizarron, int numeroDeAula, int capacidad, int piso, int idAula, String caracteristicas, boolean aireAcondicionado, boolean habilitado) {
        super(tipoPizarron, numeroDeAula, capacidad, piso, idAula, caracteristicas, aireAcondicionado, habilitado);
        this.televisor = televisor;
        this.proyector = proyector;
        this.computadora = computadora;
        this.ventilador = ventilador;
    }
}
