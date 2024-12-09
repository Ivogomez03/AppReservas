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

        Iterable <Periodo> periodos = periodoDAO.findAll();
        for (Periodo periodo : periodos) {
            if (reserva.isPeriodicaAnual() && periodo.getTipoPeriodo()==TipoPeriodo.ANUAL && periodo.getFechaInicio().getYear()==LocalDate.now().getYear()) {
                return periodo;
            }
            else if(reserva.isPeriodicaPrimerCuatrimestre() && periodo.getTipoPeriodo()==TipoPeriodo.PRIMERCUATRIMESTRE && periodo.getFechaInicio().getYear()==LocalDate.now().getYear()){
                return periodo;
            }
            else if(reserva.isPeriodicaSegundoCuatrimestre() && periodo.getTipoPeriodo()==TipoPeriodo.SEGUNDOCUATRIMESTRE && periodo.getFechaInicio().getYear()==LocalDate.now().getYear()){
                return periodo;
            }
            else{
                throw new ValidationException("No se encontro el periodo para este año");
            }
        }
        return null;
    }
}
