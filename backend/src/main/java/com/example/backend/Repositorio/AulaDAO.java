package com.example.backend.Repositorio;

import com.example.backend.Modelos.*;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository public interface AulaDAO extends JpaRepository<Aula, Integer> { // Métodos personalizados si es necesario
    // Método para buscar un aula por su número de aula
    Aula findByNumeroDeAula(int numeroDeAula);
    @Override
    List<Aula> findAll();
}
    