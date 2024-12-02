package com.example.backend.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.Modelos.Dia;
import com.example.backend.Modelos.Esporadica;
import com.example.backend.Modelos.Periodica;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ReservaDAO extends JpaRepository<Dia, Integer> {
@Query("SELECT r FROM Periodica r WHERE r.periodo.idPeriodo = :idPeriodo") 
 List<Periodica> obtenerReservasPorPeriodo(@Param("idPeriodo") int idPeriodo); 
@Query("SELECT r FROM Esporadica r JOIN r.fechaEspecifica f WHERE f.fecha = :fecha") 
        List<Esporadica> obtenerReservasPorFecha(@Param("fecha") LocalDate fecha);

    
}