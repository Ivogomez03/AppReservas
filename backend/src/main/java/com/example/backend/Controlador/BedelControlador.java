package com.example.backend.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.DTO.BedelDTO;
import com.example.backend.DTO.BusquedaDTO;
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

    @PatchMapping("/bedel/CU14/modificarBedel")
    public String ModificarBedel(@RequestBody BedelDTO bedeldto) {
       return bedelServicio.modificarBedel(bedeldto);
    }

    @GetMapping("/bedel/CU14/obetenerBedel")
    public BedelDTO obtenerBedel(@RequestBody int id) {
        return bedelServicio.obtenerBedel(id);
    }

    @DeleteMapping("/bedel/CU15")
    public String eliminarBedel(@RequestBody int id) {
        return bedelServicio.eliminarBedel(id);
    }

    @GetMapping("/bedel/CU16")
    public List<BedelDTO> obtenerBedel(BusquedaDTO busqueda) {
        return bedelServicio.obtenerBedel(busqueda);
    }
}
