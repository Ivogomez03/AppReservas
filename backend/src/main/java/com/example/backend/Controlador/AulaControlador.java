package com.example.backend.Controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.backend.DTO.SalidaCU9DTO;
import com.example.backend.DTO.BuscarAulaDTO;
import com.example.backend.DTO.ModificarAulaDTO;
import com.example.backend.Servicio.Implementacion.AulaServicio;
import com.example.backend.Excepciones.ValidationException;
import java.util.Collections;
import java.util.List;

@RestController
public class AulaControlador {

    @Autowired
    private AulaServicio aulaServicio;

    @GetMapping("/buscarAula")
    public ResponseEntity<List<SalidaCU9DTO>> buscarAula(@RequestParam(required = false) Integer numeroDeAula,
            @RequestParam(required = false) String tipoAula, @RequestParam(required = false) Integer capacidad) {
        try {
            // Construir el DTO con los parámetros
            BuscarAulaDTO buscarAulaDTO = new BuscarAulaDTO();
            Integer numeroDeAulaLimpio = (numeroDeAula != null && numeroDeAula > 0) ? numeroDeAula : null;
            Integer capacidadLimpia = (capacidad != null && capacidad > 0) ? capacidad : null;
            String tipoAulaLimpio = (tipoAula != null && !tipoAula.trim().isEmpty()) ? tipoAula : null;
            buscarAulaDTO.setNumeroDeAula(numeroDeAulaLimpio);
            buscarAulaDTO.setTipoAula(tipoAulaLimpio);
            buscarAulaDTO.setCapacidad(capacidadLimpia);

            // Pasar el DTO al servicio
            List<SalidaCU9DTO> listaAulas = aulaServicio.buscarAulas(buscarAulaDTO);
            return ResponseEntity.ok(listaAulas);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonList(new SalidaCU9DTO()));
        }
    }

    @PutMapping("/modificarAula")
    public ResponseEntity<String> modificarAula(@RequestBody ModificarAulaDTO dto) {
        try {
            String salida = aulaServicio.modificarAula(dto);
            return ResponseEntity.ok(salida);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
