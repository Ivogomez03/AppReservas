package com.example.backend.Repositorio;

import com.example.backend.Modelos.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository public interface AulaDAO extends JpaRepository<Aula, Integer> { // Métodos personalizados si es necesario
    
}
    