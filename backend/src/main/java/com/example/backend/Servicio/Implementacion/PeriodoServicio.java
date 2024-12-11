package com.example.backend.Servicio.Implementacion;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Excepciones.ValidationException;
import com.example.backend.Modelos.Periodo;
import com.example.backend.Modelos.TipoPeriodo;
import com.example.backend.Repositorio.PeriodoDAO;
import com.example.backend.Servicio.IPeriodoServicio;


@Service
public class PeriodoServicio implements IPeriodoServicio {

    @Autowired
    private PeriodoDAO periodoDAO;
    
    public Periodo obtenerPeriodo(ReservaDTO reserva) {

        Iterable <Periodo> periodos2;
        Periodo periodoU = null;
        if (reserva.isPeriodicaAnual()) {
            periodos2 = periodoDAO.findByTipoPeriodo(TipoPeriodo.ANUAL);
        }
        else if(reserva.isPeriodicaPrimerCuatrimestre()){
            periodos2 = periodoDAO.findByTipoPeriodo(TipoPeriodo.PRIMERCUATRIMESTRE);
        }
        else if(reserva.isPeriodicaSegundoCuatrimestre()){
            periodos2 = periodoDAO.findByTipoPeriodo(TipoPeriodo.SEGUNDOCUATRIMESTRE);
        }
        else{
            throw new ValidationException("No se encontro el periodo para esta reserva");
        }
        for(Periodo periodo : periodos2){
            if(periodo.getFechaInicio().getYear()==LocalDate.now().getYear()){
                periodoU = new Periodo();
                periodoU = periodo;
            }
        }
        if(periodoU!=null){
            return periodoU;
        }
        else{
            throw new ValidationException("No se encontro el periodo para esta reserva");
        }
    }
}
