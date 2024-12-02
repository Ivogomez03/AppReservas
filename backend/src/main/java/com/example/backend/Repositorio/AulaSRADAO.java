
package com.example.backend.Repositorio;

import com.example.backend.Modelos.AulaSinRecursosAdicionales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaSRADAO extends JpaRepository<AulaSinRecursosAdicionales, Integer> {

    @Query("SELECT a FROM AulaSinRecursosAdicionales a WHERE (:numeroDeAula IS NULL OR a.numeroDeAula = :numeroDeAula) AND (:capacidadMinima IS NULL OR a.capacidad >= :capacidadMinima)")
    List<AulaSinRecursosAdicionales> buscarPorCriterio(@Param("numeroDeAula") Integer numeroDeAula, @Param("capacidadMinima") Integer capacidadMinima);
}
