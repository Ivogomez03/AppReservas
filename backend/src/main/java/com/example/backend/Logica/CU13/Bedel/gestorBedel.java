package com.example.backend.Logica.CU13.Bedel;
import com.example.backend.Logica.CU13.CU13DAO.BedelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bedel")
public class gestorBedel {

    @Autowired
    private BedelDAO bedelDAO;
   
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

        if (bedelDAO.existeBedelPorID(bedelDTO.getIdUsuario())) {
            return "El ID del Bedel ya existe.";
        } 
        else if(contra.length() < 4) {
            return "La contraseña debe tener al menos 4 caracteres.";
        }
        else if(contra.chars().anyMatch(Character::isDigit)){
            return "La contraseña debe tener al menos un número.";
        }
        else if(contra.chars().anyMatch(Character::isUpperCase)){
            return "La contraseña debe tener al menos una mayúscula.";
        }
        else if(!caracteresEspeciales){
            return "La contraseña debe tener al menos un caracter especial.";
        }

        else {
            bedelDAO.guardarBedel(bedelDTO);
            return "Bedel creado exitosamente.";
        }
    }

    @GetMapping("/listar")
    public List<BedelDTO> listarBedeles() {
        return bedelDAO.recuperarBedeles();
    }
}