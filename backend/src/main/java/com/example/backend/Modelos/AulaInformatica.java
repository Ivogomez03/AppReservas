package com.example.backend.Modelos;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Entity
public class AulaInformatica extends Aula{
    private int cantidadDeComputadoras;
    private boolean canion;

    public AulaInformatica() {
    }
    public AulaInformatica(int cantidadDeComputadoras, boolean proyector, String tipoPizarron, int numeroDeAula, int capacidad, int piso, int idAula, String caracteristicas, boolean aireAcondicionado, boolean habilitado) {
        super(tipoPizarron, numeroDeAula, capacidad, piso, idAula, caracteristicas, aireAcondicionado, habilitado);
        this.cantidadDeComputadoras = cantidadDeComputadoras;
        this.canion = proyector;
    }

    public boolean setCantidadDeComputadoras(int cantidadDeComputadoras) {
        if(cantidadDeComputadoras > 0 && cantidadDeComputadoras <= this.getCapacidad()){
            this.cantidadDeComputadoras = cantidadDeComputadoras;
            return true;
        }
        return false;
    }
    @Override
    public String getTipoAula() {
        return "Informatica";
    }
}
