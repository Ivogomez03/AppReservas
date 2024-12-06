package com.example.backend.Servicio;

import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Modelos.Esporadica;
import com.example.backend.Modelos.FechaEspecifica;
import java.util.List;
import com.example.backend.DTO.CDU01ReservaYAulaFinal;

public interface IFechaEspecificaservicio {
    public List<FechaEspecifica> crearFechasEspecificas(ReservaDTO reserva, List<CDU01ReservaYAulaFinal> reservaYAula, Esporadica esporadica);
}
