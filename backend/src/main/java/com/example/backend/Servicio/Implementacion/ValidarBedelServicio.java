package com.example.backend.Servicio.Implementacion;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.Repositorio.BedelDAO;
import com.example.backend.Servicio.IValidarBedelServicio;

@Service
public class ValidarBedelServicio implements IValidarBedelServicio {

    @Autowired
    private BedelDAO bedelDAO;
    
    private static final Pattern PASSWORD_PATTERN = 
        Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*]).{8,16}$");

    public String validatePassword(String contra) {
        if (!PASSWORD_PATTERN.matcher(contra).matches()) {
            return "La contraseña debe tener al menos de 8 caracteres, un maximo de 16 caracteres, al menos un número, una mayúscula y un caracter especial.";
        }
        return "Contraseña valida";
    }
    public String validarId(int id) {

        if(bedelDAO.findById(id).isPresent()){
            return "Id ya existente";
        }
        else{
            return "Id valida";
        }
    
    }
    
    

}
