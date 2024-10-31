package com.example.backend.Logica.CU13.CU13DAO;
import java.util.List;

import com.example.backend.Logica.CU13.Bedel.Bedel;
import com.example.backend.Logica.CU13.Bedel.BedelDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
@Repository
public class BedelDAOImpl implements BedelDAO{

    @PersistenceContext
    private EntityManager entityManager;

    
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
    public boolean existeBedelPorID(int idUsuario) {
        String sql = "SELECT COUNT(b) FROM Bedel b WHERE b.idUsuario = :idUsuario";
        Long count = (Long) entityManager.createQuery(sql)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public List<BedelDTO> recuperarBedeles() {
        String sql = "SELECT new com.example.backend.Logica.CU13.BedelDTO(b.nombre, b.apellido, b.idUsuario,b.contrasena, b.turnoDeTrabajo, b.idAdminCreador) FROM Bedel b";
        return entityManager.createQuery(sql, BedelDTO.class).getResultList();
    }
}
