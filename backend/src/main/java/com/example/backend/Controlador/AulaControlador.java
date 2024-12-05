package com.example.backend.Controlador;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.example.backend.DTO.AulaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.backend.DTO.SalidaCU9DTO;
import com.example.backend.DTO.BuscarAulaDTO;
import com.example.backend.DTO.ModificarAulaDTO;
import com.example.backend.Servicio.Implementacion.AulaServicio;
import com.example.backend.Excepciones.ValidationException;
import com.example.backend.Modelos.Aula;
import com.example.backend.Modelos.DiaSemana;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

@RestController
public class AulaControlador {

    @Autowired
    private AulaServicio aulaServicio;
    
    @GetMapping("/buscarAula")
    public ResponseEntity<List<SalidaCU9DTO>> buscarAula(@RequestParam(required = false) Integer numeroDeAula,@RequestParam(required = false) String tipoAula,@RequestParam(required = false) Integer capacidad) {
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
    public ResponseEntity<String> modificarAula(@RequestBody ModificarAulaDTO dto){
        try {
            String salida = aulaServicio.modificarAula(dto);
            return ResponseEntity.ok(salida);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
@GetMapping("/disponibles-periodicas")
public ResponseEntity<List<AulaDTO>> obtenerAulasDisponiblesPeriodicasConPeriodo(
        @RequestParam Class<? extends Aula> tipoClase,
        @RequestParam int periodo,
        @RequestParam DiaSemana diaSemana,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaFin) {
    List<AulaDTO> aulasDisponibles = aulaServicio.obtenerAulasDisponiblesPeriodicasConPeriodo(tipoClase, periodo, diaSemana, horaInicio, horaFin);
    return new ResponseEntity<>(aulasDisponibles, HttpStatus.OK);
}


    @GetMapping("/disponibles-esporadicas")
    public ResponseEntity<List<AulaDTO>> obtenerAulasDisponiblesEsporadicas(
            @RequestParam Class<? extends Aula> tipoClase,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaFin) {
        List<AulaDTO> aulasDisponibles = aulaServicio.obtenerAulasDisponiblesEsporadicas(tipoClase, fecha, horaInicio, horaFin);
        return new ResponseEntity<>(aulasDisponibles, HttpStatus.OK);
    }


}
