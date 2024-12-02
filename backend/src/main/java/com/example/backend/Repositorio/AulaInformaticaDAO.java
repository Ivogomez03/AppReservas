package com.example.backend.Repositorio;

import org.springframework.data.repository.CrudRepository;
import com.example.backend.Modelos.AulaInformatica;
public interface AulaInformaticaDAO extends CrudRepository<Administrador, Integer>{
    @Query("SELECT a FROM AulaInformatica a WHERE " +
           "(:numeroAula IS NULL OR a.nroAula = :numeroAula) AND " +
           "(:capacidadMinima IS NULL OR a.capacidad >= :capacidadMinima)")
    List<AulaInformatica> buscarPorCriterio(@Param("numeroAula") Integer numeroAula, 
                                           @Param("capacidadMinima") Integer capacidadMinima);
}