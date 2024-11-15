package com.example.backend.Servicio.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.Repositorio.AdministradorDAO;
import com.example.backend.Modelos.Administrador;
import com.example.backend.Servicio.IAdministradorServicio;

@Service
public class AdministradorServicio implements  IAdministradorServicio{
    @Autowired
    private AdministradorDAO adminDAO;

    public Administrador buscarAdministrador(int id){
        return adminDAO.findById(id).orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

    }
}
