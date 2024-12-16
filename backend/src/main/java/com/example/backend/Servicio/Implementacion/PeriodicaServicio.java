package com.example.backend.Servicio.Implementacion;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.DTO.CDU01ReservaYAulaFinal;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Modelos.Dia;
import com.example.backend.Modelos.Periodica;
import com.example.backend.Repositorio.DiaDAO;
import com.example.backend.Repositorio.PeriodicaDAO;
import com.example.backend.Servicio.IPeriodicaServicio;

import jakarta.transaction.Transactional;

@Service
public class PeriodicaServicio implements IPeriodicaServicio {

    @Autowired
    private PeriodicaDAO periodicaDAO;

    @Autowired
    private PeriodoServicio periodoServicio;

    @Autowired
    private DiaServicio diaServicio;

    @Autowired
    private DiaDAO diaDAO;

    @Transactional
    @Override
    public void guardarReservaPeriodica(ReservaDTO reservaDTO, List<CDU01ReservaYAulaFinal> reservaYAula) {
        System.out.println("En periodica el dto es: " + reservaYAula);
        Periodica periodica = new Periodica();
        List<Dia> dias = diaServicio.crearDias(reservaYAula, periodica);
        periodica.setIdReserva(reservaDTO.getIdReserva());
        periodica.setNombreProfesor(reservaDTO.getNombreProfesor());
        periodica.setApellidoProfesor(reservaDTO.getApellidoProfesor());
        periodica.setCorreo(reservaDTO.getCorreo());
        periodica.setNombreCatedra(reservaDTO.getNombreCatedra());
        periodica.setIdProfesor(reservaDTO.getIdProfesor());
        periodica.setIdCatedra(reservaDTO.getIdCatedra());
        periodica.setPeriodo(periodoServicio.obtenerPeriodo(reservaDTO));

        for(Dia dia : dias) {
            diaDAO.save(dia);
        }

        periodica.setDias(dias);
        
        periodicaDAO.save(periodica);
    }
}
