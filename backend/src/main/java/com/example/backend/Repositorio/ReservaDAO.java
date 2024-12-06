package com.example.backend.Repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.Modelos.Dia;
import com.example.backend.Modelos.Esporadica;
import com.example.backend.Modelos.Periodica;

@Repository
public interface ReservaDAO extends JpaRepository<Dia, Integer> {
    @Query("SELECT r FROM Periodica r WHERE r.periodo.idPeriodo = :idPeriodo") 
    List<Periodica> obtenerReservasPorPeriodo(@Param("idPeriodo") int idPeriodo); 
    @Query("SELECT r FROM Periodica r JOIN r.periodo p WHERE p.fechaInicio <= :fechaFinPeriodo AND p.fechaFin >= :fechaInicioPeriodo")
    List<Periodica> obtenerReservasPorFechasPeriodo(@Param("fechaInicioPeriodo") LocalDate fechaInicioPeriodo, @Param("fechaFinPeriodo") LocalDate fechaFinPeriodo);
    @Query("SELECT r FROM Esporadica r JOIN r.fechaEspecifica f WHERE f.fecha = :fecha") 
       List<Esporadica> obtenerReservasPorFecha(@Param("fecha") LocalDate fecha);
    @Query("SELECT r FROM Periodica r WHERE r.nombreCatedra = :nombreCatedra AND YEAR(r.periodo.fechaInicio) = :anio")
    List<Periodica> obtenerReservasPeriodicasPorNombreCatedra(@Param("nombreCatedra") String nombreCatedra, @Param("anio") int anio);
    @Query("SELECT r FROM Esporadica r JOIN r.fechaEspecifica f WHERE r.nombreCatedra = :nombreCatedra AND YEAR(f.fecha) = :anio")
    List<Esporadica> obtenerReservasEsporadicasPorNombreCatedra(@Param("nombreCatedra") String nombreCatedra, @Param("anio") int anio);
}