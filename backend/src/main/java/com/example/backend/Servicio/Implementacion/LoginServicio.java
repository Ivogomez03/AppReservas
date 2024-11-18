package com.example.backend.Servicio.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.Servicio.Implementacion.BedelServicio;
import com.example.backend.Servicio.Implementacion.AdministradorServicio;
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

        if(gestorAdmin.buscarAdministrador(loginDTO.getIdUsuario()).getContrasena == loginDTO.getContrasena() ){
            return 1;
        }
        else if(gestorBedel.buscarBedel(loginDTO.getIdUsuario()).getContrasena == loginDTO.getContrasena()){
            return 2;
        }else{
            return 0;
        }

        
    }
    
}