package com.example.backend.Controlador;

import java.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.DTO.PeriodicaDTO;
import com.example.backend.Servicio.Implementacion.PeriodicaServicio;
import com.example.backend.Servicio.Implementacion.ReservaServicio;

@RestController
public class ReservaControlador {

    @Autowired
    private PeriodicaServicio reservaServicio;

    @GetMapping("/reservar/periodica")
    private void reservar(PeriodicaDTO reservaPeriodica){ {
        
    }

    

}
