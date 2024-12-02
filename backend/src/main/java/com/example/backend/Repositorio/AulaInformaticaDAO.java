package com.example.backend.Repositorio;

import org.springframework.data.repository.CrudRepository;

import com.example.backend.DTO.BuscarAulaDTO;
import com.example.backend.Modelos.Aula;
import com.example.backend.Modelos.AulaInformatica;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface AulaInformaticaDAO extends CrudRepository<AulaInformatica, Integer>{
     @Query("SELECT a FROM AulaInformatica a WHERE (:numeroAula IS NULL OR a.numeroDeAula = :numeroAula) AND (:capacidadMinima IS NULL OR a.capacidad >= :capacidadMinima)")
    List<AulaInformatica> buscarPorCriterio(@Param("numeroAula") Integer numeroAula, @Param("capacidadMinima") Integer capacidadMinima);



}