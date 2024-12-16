package com.example.backend.Servicio.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.DTO.LoginDTO;
import com.example.backend.Servicio.ILoginServicio;

@Service
public class LoginServicio implements ILoginServicio {
    @Autowired 
    private AdministradorServicio gestorAdmin;

    @Autowired 
    private BedelServicio gestorBedel;

    @Override
    public int validarUsuario(LoginDTO loginDTO) {
        System.out.println("Attempting login for idUsuario: " + loginDTO.getIdUsuario()); // Log the idUsuario

        if(gestorAdmin.validarAdministrador(loginDTO.getIdUsuario(), loginDTO.getContrasena())){
            System.out.println("Login successful for Admin with idUsuario: " + loginDTO.getIdUsuario()); // Log success for Admin
            return 1;
        }
        else if(gestorBedel.validarBedel(loginDTO.getIdUsuario(), loginDTO.getContrasena())){
            System.out.println("Login successful for Bedel with idUsuario: " + loginDTO.getIdUsuario()); // Log success for Bedel
            return 2;
        } else {
            System.out.println("Login failed for idUsuario: " + loginDTO.getIdUsuario()); // Log failure
            return 0;
        }
    }
}