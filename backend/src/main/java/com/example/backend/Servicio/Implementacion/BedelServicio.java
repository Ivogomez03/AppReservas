package com.example.backend.Servicio.Implementacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.Modelos.Bedel;
import com.example.backend.Modelos.TurnoDetrabajo;
import com.example.backend.Repositorio.BedelDAO;
import com.example.backend.Servicio.IBedelServicios;

@Service
public class BedelServicio implements IBedelServicios {

    @Autowired
    private BedelDAO bedelDAO;

    @Override
    public List<Bedel> getBedeles() {
        List<Bedel> bedeles = bedelDAO.findAll();
        return bedeles;
    }

    @Override
    public Bedel getBedelPorId(int id) {
        
        Bedel bedel = bedelDAO.findById(id).orElse(null);
        return bedel;
    }

    @Override
    public void guardarBedel(Bedel bedel) {
        bedelDAO.save(bedel);
    }

    @Override
    public void eliminarBedel(int id) {
        bedelDAO.deleteById(id);
    }

    @Override
    public void modificarBedel(int idUsuarioVieja, int idUsuarioNueva, String nombre, String apellido, String contrasena, TurnoDeTrabajo turnoDeTrabajo, int idAdminCreador) {
        
        Bedel bedel = this.getBedelPorId(idUsuarioVieja);
        bedel.setIdUsuario(idUsuarioNueva);
        bedel.setNombre(nombre);
        bedel.setApellido(apellido);
        bedel.setContrasena(contrasena);
        bedel.setTurnoDeTrabajo(turnoDeTrabajo);
        bedel.setIdAdminCreador(idAdminCreador);
        this.guardarBedel(bedel);

    }

    

}
