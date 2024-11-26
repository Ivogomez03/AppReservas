package com.example.backend.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.backend.DTO.BedelDTO;
import com.example.backend.DTO.ValidarContrasenaDTO;
import com.example.backend.Servicio.Implementacion.BedelServicio;
import com.example.backend.Modelos.TurnoDeTrabajo;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class BedelControlador {

    @Autowired
    private BedelServicio bedelServicio;

    //Endpoint para CU13
    @PostMapping("/bedel/CU13")
    public ValidarContrasenaDTO validarBedel(@RequestBody BedelDTO bedeldto) {
        return bedelServicio.validarBedel(bedeldto);
    }
    @PostMapping("/bedel/CU15")
     public ResponseEntity<String> eliminarBedel(@RequestBody BedelDTO bedelSeleccionado) {
        try {
            bedelServicio.eliminarBedel(bedelSeleccionado);
            return ResponseEntity.ok("El bedel ha sido deshabilitado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/bedel/CU16")
    public ResponseEntity<List<BedelDTO>> buscarPorTurnoyApellido(@RequestParam(required = false) String apellido,@RequestParam(required = false) TurnoDeTrabajo turno) {
        try {
            // Llama al servicio para buscar los bedeles
            List<BedelDTO> resultado = bedelServicio.buscarBedelesPorTurnoyApellido(turno, apellido);
            return ResponseEntity.ok(resultado); // Devuelve 200 OK con la lista de resultados
        } catch (IllegalArgumentException e) {
            // Devuelve 400 Bad Request con una lista vacía si ocurre una excepción
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @PostMapping("/bedel/CU14/obtenerDatos")
    public ResponseEntity<BedelDTO> obtenerDatosBedel(@RequestBody BedelDTO bedelDTO) {
        try {
            BedelDTO datosCompletos = bedelServicio.obtenerDatosBedel(bedelDTO);
            return ResponseEntity.ok(datosCompletos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
@PostMapping("/bedel/CU14/modificarBedel")
    public ResponseEntity<BedelDTO> modificarBedel(@RequestBody BedelDTO bedelModificado) {
        try {
            BedelDTO bedelActualizado = bedelServicio.modificarBedel(bedelModificado);
            return ResponseEntity.ok(bedelActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}
