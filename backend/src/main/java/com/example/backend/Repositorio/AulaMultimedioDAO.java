package com.example.backend.Repositorio;

import org.springframework.data.repository.CrudRepository;
import com.example.backend.Modelos.AulaMultimedio;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface AulaMultimedioDAO extends CrudRepository<AulaMultimedio, Integer>{
    @Query("SELECT a FROM AulaMultimedio a WHERE " +
           "(:numeroAula IS NULL OR a.nroAula = :numeroAula) AND " +
           "(:capacidadMinima IS NULL OR a.capacidad >= :capacidadMinima)")
    List<AulaMultimedio> buscarPorCriterio(@Param("numeroAula") Integer numeroAula, 
                                           @Param("capacidadMinima") Integer capacidadMinima);
}