package com.example.backend.Repositorio;

import org.springframework.data.repository.CrudRepository;
import com.example.backend.Modelos.AulaSinRecursosAdicionales;
public interface AulaSRADAO extends CrudRepository<Administrador, Integer>{
    @Query("SELECT a FROM AulaSinRecursosAdicionales a WHERE " +
           "(:numeroAula IS NULL OR a.nroAula = :numeroAula) AND " +
           "(:capacidadMinima IS NULL OR a.capacidad >= :capacidadMinima)")
    List<AulaSinRecursosAdicionales> buscarPorCriterio(@Param("numeroAula") Integer numeroAula, 
                                           @Param("capacidadMinima") Integer capacidadMinima);
}