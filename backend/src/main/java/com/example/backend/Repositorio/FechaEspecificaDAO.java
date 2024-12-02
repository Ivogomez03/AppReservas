package com.example.backend.Repositorio;

import org.springframework.data.repository.CrudRepository;

import com.example.backend.Modelos.FechaEspecifica;

public interface FechaEspecificaDAO extends CrudRepository<FechaEspecifica, Integer> {
  List<FechaEspecifica> findByFecha(LocalDate fecha);
}
