package com.example.backend.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.DTO.BedelDTO;
import com.example.backend.DTO.ValidarContrasenaDTO;
import com.example.backend.Servicio.Implementacion.BedelServicio;

@RestController
public class BedelControlador {

    @Autowired
    private BedelServicio bedelServicio;

    //Endpoint para CU13
    @PostMapping("/bedel/CU13")
    public ValidarContrasenaDTO validarBedel(@RequestBody BedelDTO bedeldto) {
        return bedelServicio.validarBedel(bedeldto);
    }

}
