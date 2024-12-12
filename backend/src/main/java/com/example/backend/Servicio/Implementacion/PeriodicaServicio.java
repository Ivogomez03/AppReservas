package com.example.backend.Servicio.Implementacion;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.DTO.CDU01ReservaYAulaFinal;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Modelos.Periodica;
import com.example.backend.Repositorio.PeriodicaDAO;
import com.example.backend.Servicio.IPeriodicaServicio;

@Service
public class PeriodicaServicio implements IPeriodicaServicio {

    @Autowired
    private PeriodicaDAO periodicaDAO;

    @Autowired
    private PeriodoServicio periodoServicio;

    @Autowired
    private DiaServicio diaServicio;

    @Override
    public void guardarReservaPeriodica(ReservaDTO reservaDTO, List<CDU01ReservaYAulaFinal> reservaYAula) {
        System.out.println("En periodica el dto es: " + reservaYAula);
        Periodica periodica = new Periodica();
        periodica.setIdReserva(reservaDTO.getIdReserva());
        periodica.setNombreProfesor(reservaDTO.getNombreProfesor());
        periodica.setApellidoProfesor(reservaDTO.getApellidoProfesor());
        periodica.setCorreo(reservaDTO.getCorreo());
        periodica.setNombreCatedra(reservaDTO.getNombreCatedra());
        periodica.setIdProfesor(reservaDTO.getIdProfesor());
        periodica.setIdCatedra(reservaDTO.getIdCatedra());
        periodica.setPeriodo(periodoServicio.obtenerPeriodo(reservaDTO));
        periodica.setDias(diaServicio.crearDias(reservaYAula, periodica));
        periodicaDAO.save(periodica);
    }
}
