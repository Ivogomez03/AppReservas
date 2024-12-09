package com.example.backend.Servicio.Implementacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.DTO.CDU01ReservaYAulaFinal;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Modelos.Esporadica;
import com.example.backend.Repositorio.EsporadicaDAO;
import com.example.backend.Servicio.IEsporadicaServicio;

@Service
public class EsporadicaServicio implements IEsporadicaServicio {

    @Autowired
    private FechaEspecificaServicio fechaEspecificaServicio;

    @Autowired
    private EsporadicaDAO esporadicaDAO;

    @Override
    public void guardarReservaEsporadica(List<CDU01ReservaYAulaFinal> reservaYAula, ReservaDTO reservaDTO) {
        Esporadica esporadica = new Esporadica();
        esporadica.setIdReserva(reservaDTO.getIdReserva());
        esporadica.setNombreProfesor(reservaDTO.getNombreProfesor());
        esporadica.setApellidoProfesor(reservaDTO.getApellidoProfesor());
        esporadica.setCorreo(reservaDTO.getCorreo());
        esporadica.setNombreCatedra(reservaDTO.getNombreCatedra());
        esporadica.setIdProfesor(reservaDTO.getIdProfesor());
        esporadica.setIdCatedra(reservaDTO.getIdCatedra());
        esporadica.setFechaEspecifica(fechaEspecificaServicio.crearFechasEspecificas(reservaDTO, reservaYAula, esporadica));
        esporadicaDAO.save(esporadica);

    }

}
