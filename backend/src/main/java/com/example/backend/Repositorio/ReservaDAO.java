package com.example.backend.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.Modelos.Dia;

@Repository
public interface ReservaDAO extends JpaRepository<Dia, Integer> {


    
}