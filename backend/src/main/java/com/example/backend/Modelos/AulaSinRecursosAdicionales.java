package com.example.backend.Modelos;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AulaSinRecursosAdicionales extends Aula{
    private boolean ventilador;

    public AulaSinRecursosAdicionales() {
    }
    public AulaSinRecursosAdicionales(boolean ventilador, String tipoPizarron, int numeroDeAula, int capacidad, int piso, int idAula, String caracteristicas, boolean aireAcondicionado, boolean habilitado) {
        super(tipoPizarron, numeroDeAula, capacidad, piso, idAula, caracteristicas, aireAcondicionado, habilitado);
        this.ventilador = ventilador;
    }

    public boolean setVentilador(boolean ventilador) {
        if(this.isAireAcondicionado() && ventilador){
            return false; 
        }
        else{
            this.ventilador = ventilador;
            return true;
        }
    }
}
