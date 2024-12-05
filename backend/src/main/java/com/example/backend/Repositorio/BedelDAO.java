package com.example.backend.Repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.backend.Modelos.Bedel;
import com.example.backend.Modelos.TurnoDeTrabajo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BedelDAO extends CrudRepository<Bedel, Integer> {
    List<Bedel> findByApellidoAndHabilitadoTrue(String apellido);

    List<Bedel> findByTurnoDeTrabajoAndHabilitadoTrue(TurnoDeTrabajo turno);
     List<Bedel> findByHabilitadoTrue();

    @Query("SELECT b FROM Bedel b WHERE b.apellido = :apellido AND b.turnoDeTrabajo = :turno AND b.habilitado = true")
    List<Bedel> buscarPorApellidoYTurno(@Param("apellido") String apellido, @Param("turno") TurnoDeTrabajo turno);

}
