package com.example.backend.Servicio.Implementacion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.backend.DTO.CDU01ReservaYAulaFinal;
import com.example.backend.DTO.DiaPeriodicaDTO;
import com.example.backend.DTO.PeriodicaDTO;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Modelos.DiaSemana;
import com.example.backend.Modelos.Periodica;
import com.example.backend.Repositorio.PeriodicaDAO;
import com.example.backend.Servicio.IPeriodicaServicio;

public class PeriodicaServicio implements IPeriodicaServicio{

    @Autowired
    private PeriodicaDAO periodicaDAO;

    @Autowired
    private PeriodoServicio periodoServicio;

    @Autowired
    private DiaServicio diaServicio;
    
    @Override
    public boolean validarDias(PeriodicaDTO periodoDTO) {
        List<DiaPeriodicaDTO> diasPeriodicos = periodoDTO.getDiasPeriodicos();
        
        Set<DiaSemana> diasUnicos = new HashSet<>();
        
        for (DiaPeriodicaDTO dia : diasPeriodicos) {
            
            if (!diasUnicos.add(dia.getDiaSemana())) {
                
                return false; 
            }
        }
        
        return true; 
    }

    @Override
    public boolean validarHorarios(PeriodicaDTO periodoDTO) { 
        List<DiaPeriodicaDTO> diasPeriodicos = periodoDTO.getDiasPeriodicos();
        boolean duracion[] = new boolean[diasPeriodicos.size()];
        int posicion = diasPeriodicos.size();
        
        for (DiaPeriodicaDTO dia : diasPeriodicos) {
                
            if ((dia.getDuracion().getHour()*60 + dia.getDuracion().getMinute()) % 30 == 0) {
                duracion[posicion] = true; 
            }
            else{
                duracion[posicion] = false;
            }
            posicion++;
                
        }
        
        return true; 
    }

    @Override
    public void guardarReservaPeriodica(ReservaDTO reservaDTO, List<CDU01ReservaYAulaFinal> reservaYAula) {
        
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
