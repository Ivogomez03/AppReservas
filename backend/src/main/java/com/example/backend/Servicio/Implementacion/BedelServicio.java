package com.example.backend.Servicio.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.DTO.BedelDTO;
import com.example.backend.Modelos.CU13Salida;
import com.example.backend.Repositorio.BedelDAO;
import com.example.backend.Servicio.IBedelServicios;

@Service
public class BedelServicio implements IBedelServicios {

    @Autowired
    private BedelDAO bedelDAO;

    @Override
    public CU13Salida validarBedel(BedelDTO bedeldto) {
        
        CU13Salida salida = new CU13Salida();
        String contra = bedeldto.getContrasena();
        Boolean caracteresEspeciales = false;
        String errorContrasena = "", errorId = "";
        int validacion = 0;


        String specialChars = "@#$%&*";
        for (char c : contra.toCharArray()) {
            if (specialChars.contains(String.valueOf(c))) {
                caracteresEspeciales = true;
            }
        }

        if (bedelDAO.exiteBedelPorId(bedeldto.getIdUsuario())) {
            errorId = "Id ya existente";
        }
        else {
            validacion++;
            errorId = "Id valida";
        }
        if(!caracteresEspeciales || !contra.chars().anyMatch(Character::isUpperCase) || !contra.chars().anyMatch(Character::isDigit) || contra.length()<8 || contra.length()>16){
            errorContrasena = "La contraseña debe tener al menos de 8 caracteres, un maximo de 16 caracteres, al menos un número, una mayúscula y un caracter especial.";
        }
        else {
            validacion++;
            errorContrasena = "Contraseña valida";
        }

        if (validacion == 2) {
            bedelDAO.guardarBedel(bedeldto);
        }

        salida.setErrorContrasena(errorContrasena);
        salida.setErrorId(errorId);

        return salida;
        
    }


}