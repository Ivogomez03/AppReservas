package com.example.backend.Logica.CU13.Admin;

import java.sql.SQLException;

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

    public Boolean ControlarID(BedelDTO bedelDTO) {

        try{
            if (BedelDAO.existeBedel(bedelDTO.getIdUsuario())) {
                return false;
            }
            else {
                BedelDTO bedel = new BedelDTO(
                    bedelDTO.getNombre(),
                    bedelDTO.getApellido(),
                    bedelDTO.getIdUsuario(),
                    bedelDTO.getContrasena(),
                    bedelDTO.getTurnoDeTrabajo(),
                    bedelDTO.getID_admin_creador()
                );
                BedelDAO.guardarBedel(bedel);
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false; // O maneja la excepción de acuerdo a la lógica de tu aplicación
        }
        
    }

}
