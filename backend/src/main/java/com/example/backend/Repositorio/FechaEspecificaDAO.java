package com.example.backend.Repositorio;

import org.springframework.data.repository.CrudRepository;

import com.example.backend.Modelos.FechaEspecifica;
import java.time.LocalDate;
import java.util.List;

public interface FechaEspecificaDAO extends CrudRepository<FechaEspecifica, Integer> {
  List<FechaEspecifica> findByFecha(LocalDate fecha);
}
