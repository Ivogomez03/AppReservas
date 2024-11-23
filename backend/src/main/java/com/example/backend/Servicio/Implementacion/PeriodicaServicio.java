package com.example.backend.Servicio.Implementacion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.example.backend.DTO.DiaPeriodicaDTO;
import com.example.backend.DTO.PeriodicaDTO;
import com.example.backend.Modelos.DiaSemana;
import com.example.backend.Servicio.IPeriodicaServicio;

public class PeriodicaServicio implements IPeriodicaServicio{

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

    public void reservaPeriodica(PeriodicaDTO periodoDTO) {
        
        
    }
}
