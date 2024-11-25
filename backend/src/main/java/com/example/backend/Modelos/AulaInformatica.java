package com.example.backend.Modelos;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AulaInformatica extends Aula{
    private int cantidadDeComputadoras;
    private boolean proyector;

    public AulaInformatica() {
    }
    public AulaInformatica(int cantidadDeComputadoras, boolean proyector, String tipoPizarron, int numeroDeAula, int capacidad, int piso, int idAula, String caracteristicas, boolean aireAcondicionado, boolean habilitado) {
        super(tipoPizarron, numeroDeAula, capacidad, piso, idAula, caracteristicas, aireAcondicionado, habilitado);
        this.cantidadDeComputadoras = cantidadDeComputadoras;
        this.proyector = proyector;
    }

    public boolean setCantidadDeComputadoras(int cantidadDeComputadoras) {
        if(cantidadDeComputadoras > 0 && cantidadDeComputadoras <= this.getCapacidad()){
            this.cantidadDeComputadoras = cantidadDeComputadoras;
            return true;
        }
        return false;
    }
}
