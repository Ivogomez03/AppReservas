package com.example.backend.Repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.example.backend.Modelos.Bedel;

public interface BedelDAO extends CrudRepository<Bedel, Integer> {
    List<Bedel> findBedelByNombre(String nombre);
}
