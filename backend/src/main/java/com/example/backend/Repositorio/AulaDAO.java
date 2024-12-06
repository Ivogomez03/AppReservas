package com.example.backend.Repositorio;

import com.example.backend.Modelos.*;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository public interface AulaDAO extends JpaRepository<Aula, Integer> { // Métodos personalizados si es necesario
    // Método para buscar un aula por su número de aula
    Aula findByNumeroDeAula(int numeroDeAula);
    @Override
    List<Aula> findAll();

    @Query("SELECT a FROM Aula a WHERE (:numeroAula IS NULL OR a.numeroDeAula = :numeroAula) AND (:capacidadMinima IS NULL OR a.capacidad >= :capacidadMinima)")
    List<Aula> buscarPorCriterio(@Param("numeroAula") Integer numeroAula, @Param("capacidadMinima") Integer capacidadMinima);
}
    