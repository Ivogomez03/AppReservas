// DiaRepository.java
package com.example.backend.Repositorio;

import com.example.backend.Modelos.Dia;
import com.example.backend.Modelos.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaDAO extends JpaRepository<Dia, Integer> {
    List<Dia> findByPeriodica_Periodo_IdPeriodoAndDiaSemana(int idPeriodo, DiaSemana diaSemana);
}
