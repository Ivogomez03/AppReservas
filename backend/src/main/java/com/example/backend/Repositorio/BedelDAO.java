package com.example.backend.Repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.backend.Modelos.Bedel;
import com.example.backend.Modelos.TurnoDeTrabajo;

public interface BedelDAO extends CrudRepository<Bedel, Integer> {
    List<Bedel> findByApellidoAndHabilitadoTrue(String apellido);

    List<Bedel> findByTurnoDeTrabajoAndHabilitadoTrue(TurnoDeTrabajo turno);

    List<Bedel> findByApellidoAndTurnoDeTrabajoAndHabilitadoTrue(String apellido,TurnoDeTrabajo turno);
}
