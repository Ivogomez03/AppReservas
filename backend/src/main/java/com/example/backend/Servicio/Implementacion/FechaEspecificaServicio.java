package com.example.backend.Servicio.Implementacion;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.backend.DTO.CDU01ReservaYAulaFinal;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Modelos.Esporadica;
import com.example.backend.Modelos.FechaEspecifica;
import com.example.backend.Repositorio.FechaEspecificaDAO;
import com.example.backend.Servicio.IFechaEspecificaservicio;

public class FechaEspecificaServicio implements IFechaEspecificaservicio {

    @Autowired
    private FechaEspecificaDAO fechaEspecificaDAO;

    @Autowired
    private AulaServicio aulaServicio;

    public List<FechaEspecifica> crearFechasEspecificas(ReservaDTO reserva, List<CDU01ReservaYAulaFinal> reservaYAula, Esporadica esporadica) {

        List<FechaEspecifica> fechasEspecificas = new ArrayList<>();

        for(CDU01ReservaYAulaFinal reservaYAulaFinal : reservaYAula){
            FechaEspecifica fechaEspecifica = new FechaEspecifica();
            fechaEspecifica.setFecha(reservaYAulaFinal.getFechas().getFecha());
            fechaEspecifica.setHoraInicio(reservaYAulaFinal.getFechas().getHoraInicio());
            fechaEspecifica.setHoraFin(reservaYAulaFinal.getFechas().getHoraInicio().plusMinutes(reservaYAulaFinal.getFechas().getDuracion()));
            fechaEspecifica.setIdFechaEspecifica(0);
            fechaEspecifica.setAula(aulaServicio.convertirAEntidad(reservaYAulaFinal.getAula()));
            fechaEspecifica.setEsporadica(esporadica);
            fechaEspecificaDAO.save(fechaEspecifica);
            fechasEspecificas.add(fechaEspecifica);
        } 
        
        return fechasEspecificas;
    }

}
