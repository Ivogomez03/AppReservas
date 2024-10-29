package com.example.backend.Logica.CU13;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bedel")

public class gestorBedel {

    @PostMapping("/crear")

    public Bedel crearBedel(@RequestBody BedelDTO bedelDTO) {

        Bedel bedel = new Bedel(
                bedelDTO.getNombre(),
                bedelDTO.getApellido(),
                bedelDTO.getIdUsuario(),
                bedelDTO.getContrasena(),
                bedelDTO.getTurnoDeTrabajo()

        );

        return bedel;

    }

}
