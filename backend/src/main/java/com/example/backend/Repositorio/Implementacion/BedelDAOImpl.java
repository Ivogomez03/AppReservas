package com.example.backend.Repositorio.Implementacion;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.backend.DTO.BedelDTO;
import com.example.backend.Modelos.Bedel;
import com.example.backend.Repositorio.BedelDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class BedelDAOImpl implements BedelDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Boolean exiteBedelPorId(int idUsuario) {
        String sql = "SELECT COUNT(b) FROM Bedel b WHERE b.idUsuario = :idUsuario";
        Long count = (Long) entityManager.createQuery(sql)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();
        return count > 0;
    }

    @Override
    @Transactional
    public void guardarBedel(BedelDTO bedelDTO) {
        Bedel bedel = new Bedel();
        bedel.setNombre(bedelDTO.getNombre());
        bedel.setApellido(bedelDTO.getApellido());
        bedel.setIdUsuario(bedelDTO.getIdUsuario());
        bedel.setContrasena(bedelDTO.getContrasena()); // Cambia según sea necesario
        bedel.setTurnoDeTrabajo(bedelDTO.getTurnoDeTrabajo());
        bedel.setIdAdminCreador(bedelDTO.getIdAdminCreador());

        entityManager.persist(bedel);
    }

    @Override
    public List<BedelDTO> recuperarBedeles() {
        String sql = "SELECT new com.example.backend.DTO.BedelDTO(b.idUsuario, b.nombre, b.apellido, b.contrasena, b.turnoDeTrabajo, b.idAdminCreador) FROM Bedel b";
        return entityManager.createQuery(sql, BedelDTO.class).getResultList();
    }

}
