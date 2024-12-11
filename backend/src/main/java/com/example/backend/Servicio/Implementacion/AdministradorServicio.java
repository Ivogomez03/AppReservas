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

    @Override
    public Administrador buscarAdministrador(int id){
        return adminDAO.findById(id).orElse(null);

    }

    @Override
    public boolean validarAdministrador(int id, String contrasena) {
        // Busca el administrador por ID
        Administrador admin = buscarAdministrador(id);
        
        // Verifica si el administrador existe
        if (admin == null) {
            throw new IllegalArgumentException("El administrador con ID " + id + " no existe");
        }

        // Compara la contraseña
        if (!admin.getContrasena().equals(contrasena)) {
            throw new IllegalArgumentException("Contraseña incorrecta para el administrador con ID " + id);
        }

        // Si todo es correcto, devuelve true
        return true;
    }

}
