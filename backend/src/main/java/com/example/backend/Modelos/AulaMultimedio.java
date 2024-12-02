package com.example.backend.Modelos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AulaMultimedio extends Aula{

    private boolean televisor;
    private boolean canion;
    private boolean computadora;
    private boolean ventilador;

    public AulaMultimedio() {
    }
    public AulaMultimedio(boolean televisor, boolean proyector, boolean computadora, boolean ventilador, String tipoPizarron, int numeroDeAula, int capacidad, int piso, int idAula, String caracteristicas, boolean aireAcondicionado, boolean habilitado) {
        super(tipoPizarron, numeroDeAula, capacidad, piso, idAula, caracteristicas, aireAcondicionado, habilitado);
        this.televisor = televisor;
        this.canion = proyector;
        this.computadora = computadora;
        this.ventilador = ventilador;
    }
    @Override
    public String getTipoAula() {
        return "Multimedio";
    }
}
