package com.example.backend.Servicio.Implementacion;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.backend.DTO.CDU01ReservaYAulaFinal;
import com.example.backend.Modelos.Dia;
import com.example.backend.Modelos.Periodica;
import com.example.backend.Repositorio.DiaDAO;
import com.example.backend.Servicio.IDiaServicio;

public class DiaServicio implements IDiaServicio {

    @Autowired
    private AulaServicio aulaServicio;

    @Autowired
    private DiaDAO diaDAO;

    @Override
    public List<Dia> crearDias(List<CDU01ReservaYAulaFinal> reservaYAula, Periodica periodica) {

        List<Dia> dias = new ArrayList<>();

        for(CDU01ReservaYAulaFinal reserva : reservaYAula) {
            Dia dia = new Dia();
            dia.setDiaSemana(reserva.getDias().getDia());
            dia.setIdDia(0);
            dia.setHoraInicio(reserva.getDias().getHoraInicio());
            dia.setHoraFin(reserva.getDias().getHoraInicio().plusMinutes(reserva.getDias().getDuracion()));
            dia.setAula(aulaServicio.convertirAEntidad(reserva.getAula()));
            dia.setPeriodica(periodica);
            diaDAO.save(dia);
            dias.add(dia);
        }
        return dias;
    }
}
