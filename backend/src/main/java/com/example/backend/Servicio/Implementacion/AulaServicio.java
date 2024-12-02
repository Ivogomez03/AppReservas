package com.example.backend.Servicio.Implementacion;

import com.example.backend.DTO.AulaDTO;
import com.example.backend.Excepciones.ValidationException;
import com.example.backend.Modelos.Aula;
import com.example.backend.Modelos.AulaInformatica;
import com.example.backend.Modelos.AulaMultimedio;
import com.example.backend.Modelos.AulaSinRecursosAdicionales;

public class AulaServicio {


    public Aula crearAula(AulaDTO aulaDTO) {

        if(aulaDTO.isAulaMultimedia()){
            AulaMultimedio aula = new AulaMultimedio();
            aula.setTelevisor(aulaDTO.isTelevisor());
            aula.setProyector(aulaDTO.isProyector());
            aula.setComputadora(aulaDTO.isComputadora());
            aula.setVentilador(aulaDTO.isVentilador());

            aula.setTipoPizarron(aulaDTO.getTipoPizarron());
            aula.setNumeroDeAula(aulaDTO.getNumeroDeAula());
            aula.setCapacidad(aulaDTO.getCapacidad());
            aula.setPiso(aulaDTO.getPiso());
            aula.setIdAula(aulaDTO.getIdAula());
            aula.setCaracteristicas(aulaDTO.getCaracteristicas());
            aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());
            aula.setHabilitado(aulaDTO.isHabilitado());

            return aula;
        }
        else if(aulaDTO.isAulaInformatica()){
            AulaInformatica aula = new AulaInformatica();
            aula.setCantidadDeComputadoras(aulaDTO.getCantidadDeComputadoras());
            aula.setProyector(aulaDTO.isProyector());
            
            aula.setTipoPizarron(aulaDTO.getTipoPizarron());
            aula.setNumeroDeAula(aulaDTO.getNumeroDeAula());
            aula.setCapacidad(aulaDTO.getCapacidad());
            aula.setPiso(aulaDTO.getPiso());
            aula.setIdAula(aulaDTO.getIdAula());
            aula.setCaracteristicas(aulaDTO.getCaracteristicas());
            aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());
            aula.setHabilitado(aulaDTO.isHabilitado());

            return aula;
        }
        else if(aulaDTO.isAulaSinRecursosAdicionales()){
            AulaSinRecursosAdicionales aula = new AulaSinRecursosAdicionales();
            aula.setVentilador(aulaDTO.isVentilador());

            aula.setTipoPizarron(aulaDTO.getTipoPizarron());
            aula.setNumeroDeAula(aulaDTO.getNumeroDeAula());
            aula.setCapacidad(aulaDTO.getCapacidad());
            aula.setPiso(aulaDTO.getPiso());
            aula.setIdAula(aulaDTO.getIdAula());
            aula.setCaracteristicas(aulaDTO.getCaracteristicas());
            aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());
            aula.setHabilitado(aulaDTO.isHabilitado());

            return aula;
        }
        else{
            throw new ValidationException("Hubo un error con el tipo de reserva");
        }
    }
}
