package com.example.backend.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.Servicio.Implementacion.ReservaServicio;

@RestController
public class ReservaControlador {

    @Autowired
    private ReservaServicio reservaServicio;

    private void reservar() {
        
    }

    

}
