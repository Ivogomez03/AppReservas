
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.Repositorio;

import com.example.backend.Modelos.Periodo;
import com.example.backend.Modelos.TipoPeriodo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodoDAO extends JpaRepository<Periodo, Integer> {
    @Query("SELECT p FROM Periodo p WHERE p.tipoPeriodo = :tipoPeriodo")
    List<Periodo> findByTipoPeriodo(@Param("tipoPeriodo") TipoPeriodo tipoPeriodo);
}
