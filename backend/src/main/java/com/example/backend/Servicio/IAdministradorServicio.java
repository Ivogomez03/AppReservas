package com.example.backend.Servicio;
import com.example.backend.Modelos.Administrador;

public interface IAdministradorServicio {
    
    public Administrador buscarAdministrador(int id);

    public boolean validarAdministrador(int id, String contrasena);
}
