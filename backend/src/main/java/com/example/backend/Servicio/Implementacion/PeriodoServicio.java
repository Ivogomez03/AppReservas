package com.example.backend.Servicio.Implementacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Periodo obtenerPeriodo(ReservaDTO reserva) {

        List<Periodo> periodos2 = new ArrayList<>();

        Periodo periodoU = null;

        if (reserva.isPeriodicaAnual()) {

            periodos2.addAll(periodoDAO.findByTipoPeriodo(TipoPeriodo.ANUAL));

        } else if (reserva.isPeriodicaPrimerCuatrimestre()) {

            periodos2.addAll(periodoDAO.findByTipoPeriodo(TipoPeriodo.PRIMERCUATRIMESTRE));

        } else if (reserva.isPeriodicaSegundoCuatrimestre()) {

            periodos2.addAll(periodoDAO.findByTipoPeriodo(TipoPeriodo.SEGUNDOCUATRIMESTRE));

        } else
            throw new ValidationException("No se encontro el periodo para esta reserva");

        for (Periodo periodo : periodos2) {
            if (periodo.getFechaInicio().getYear() == LocalDate.now().getYear()) {
                periodoU = new Periodo();
                periodoU = periodo;
            }
        }
        if (periodoU != null) {
            return periodoU;
        } else {
            throw new ValidationException("No se encontro el periodo para esta reserva");
        }
    }
}
