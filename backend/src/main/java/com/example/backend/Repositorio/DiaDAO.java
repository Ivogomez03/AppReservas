package com.example.backend.Repositorio;

import com.example.backend.Modelos.Dia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface DiaDAO extends CrudRepository<Dia, Integer> {

    List<Dia> findByPeriodo_IdPeriodoAndDiaSemana(int idPeriodo, String diaSemana);
}