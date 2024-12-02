package com.example.backend.Repositorio;

import com.example.backend.Modelos.Aula;
import com.example.backend.Modelos.Dia;
import com.example.backend.Modelos.FechaEspecifica;
import com.example.backend.Modelos.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AulaDAO extends JpaRepository< idAula, Integer> {


    @Query("SELECT f.aula FROM FechaEspecifica f WHERE f.fecha = :fecha AND (f.horaInicio <= :horaFin AND f.horaFin >= :horaInicio)")
    List<Aula> findAulasOcupadasEsporadicas(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin);

     @Query("SELECT DISTINCT d.aula FROM Dia d WHERE d.diaSemana = :diaSemana AND ((d.horaInicio <= :horaFin AND d.horaFin >= :horaInicio) AND d.periodica.periodo.idPeriodo = :idPeriodo)") //al definir periodo  anual agregamos
    List<Aula> findAulasOcupadasPeriodicasConPeriodo(String diaSemana, LocalTime horaInicio, LocalTime horaFin, int idPeriodo);
    
}