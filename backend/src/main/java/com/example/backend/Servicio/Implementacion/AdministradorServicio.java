package com.example.backend.Servicio.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.Repositorio.AdministradorDAO;
import com.example.backend.Modelos.Administrador;

@Service
public class AdministradorServicio {
    @Autowired
    private AdministradorDAO adminDAO;

    public Administrador buscarAdministrador(int id){
        return adminDAO.findById(id).orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

    }
}
