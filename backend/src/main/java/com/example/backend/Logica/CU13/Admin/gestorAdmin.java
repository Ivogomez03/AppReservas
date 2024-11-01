 package com.example.backend.Logica.CU13.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.Logica.CU13.Bedel.BedelDTO;
import com.example.backend.Logica.CU13.CU13DAO.BedelDAO;

@RestController
@RequestMapping("/admin")
public class gestorAdmin {
    
     @Autowired
    
    private BedelDAO BedelDAO;

    @PostMapping("/crear")
    public String crearBedel(@RequestBody BedelDTO bedelDTO) {

        String contra = bedelDTO.getContrasena();
        Boolean caracteresEspeciales = false;

        String specialChars = "@#$%&*";
        for (char c : contra.toCharArray()) {
            if (specialChars.contains(String.valueOf(c))) {
                caracteresEspeciales = true;
            }
        }
        
        if (ControlarID(bedelDTO)) {
            return "El ID del Bedel ya existe.";
        }
        else if (contra.length() < 4) {
            System.out.println(contra.length());
            return "La contraseña debe tener al menos 4 caracteres.";
        }
        else if (!contra.chars().anyMatch(Character::isDigit)) {
            System.out.println("La contraseña no tiene números.");
            return "La contraseña debe tener al menos un número.";
        }
        else if (!contra.chars().anyMatch(Character::isUpperCase)) {
            System.out.println("La contraseña no tiene mayúsculas.");
            return "La contraseña debe tener al menos una mayúscula.";
        }
        else if (!caracteresEspeciales) {
            return "La contraseña debe tener al menos un caracter especial.";
        }
        else {
            BedelDAO.guardarBedel(bedelDTO);
            return "Bedel creado exitosamente.";
}

    }

     @SuppressWarnings("CallToPrintStackTrace")
    public Boolean ControlarID(BedelDTO bedelDTO) {

         if (BedelDAO.existeBedelPorID(bedelDTO.getIdUsuario())) {
             return true;
         } else {
             return false;
         } 
        
    }

}
