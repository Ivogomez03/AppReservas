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
    public Void crearBedel(@RequestBody BedelDTO bedelDTO) {
        boolean incorrecto = false;

        while(!incorrecto) {
            if(ControlarID(bedelDTO)){
                incorrecto = true;
            }
            else {
                //volver a pedir datos
            }
        
        }
        return null;
    }

     @SuppressWarnings("CallToPrintStackTrace")
    public Boolean ControlarID(BedelDTO bedelDTO) {

         if (BedelDAO.existeBedelPorID(bedelDTO.getIdUsuario())) {
             return false;
         } else {
             BedelDTO bedel = new BedelDTO(
                     bedelDTO.getNombre(),
                     bedelDTO.getApellido(),
                     bedelDTO.getIdUsuario(),
                     bedelDTO.getTurnoDeTrabajo(),
                     bedelDTO.getIdAdminCreador(),
                     bedelDTO.getContrasena()
             );
             BedelDAO.guardarBedel(bedel);
             return true;
         } // O maneja la excepción de acuerdo a la lógica de tu aplicación
        
    }

}
