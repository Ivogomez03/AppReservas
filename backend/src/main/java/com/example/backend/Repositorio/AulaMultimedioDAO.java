
package com.example.backend.Repositorio;

import com.example.backend.Modelos.AulaMultimedio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaMultimedioDAO extends JpaRepository<AulaMultimedio, Integer> {

    @Query("SELECT a FROM AulaMultimedio a WHERE (:numeroDeAula IS NULL OR a.numeroDeAula = :numeroDeAula) AND (:capacidadMinima IS NULL OR a.capacidad >= :capacidadMinima)")
    List<AulaMultimedio> buscarPorCriterio(@Param("numeroDeAula") Integer numeroDeAula, @Param("capacidadMinima") Integer capacidadMinima);
}
