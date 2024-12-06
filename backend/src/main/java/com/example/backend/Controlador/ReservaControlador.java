package com.example.backend.Controlador;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.DTO.ApiResponse;
import com.example.backend.DTO.CDU01ReservaYAulaFinal;
import com.example.backend.DTO.CDU01ReservasYAulas;
import com.example.backend.DTO.ErrorAlGuardar;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Excepciones.ValidationException;
import com.example.backend.Servicio.IReservaServicio;

@RestController
public class ReservaControlador {

    @Autowired
    private IReservaServicio reservaServicio;

    @PostMapping("/reserva/registrar")
    public ResponseEntity<ApiResponse<?>> registrarReserva(@RequestBody ReservaDTO reservaDTO) {
        try {
            // Llama al servicio para registrar la reserva
            List<CDU01ReservasYAulas> aulasDisponibles = reservaServicio.registrarReserva(reservaDTO);

            // Si todo está bien, devuelve la lista de aulas y el reservaDTO
            return ResponseEntity.ok(new ApiResponse<>(true, Map.of("aulas", aulasDisponibles, "reserva", reservaDTO), null));
        } catch (ValidationException ex) {
            // En caso de error, devuelve el mensaje de error
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, null, ex.getMessage()));
        }
    }
    
    @PostMapping("/reserva/guardar")
    public ResponseEntity<ErrorAlGuardar> guardarReserva(@RequestBody List<CDU01ReservaYAulaFinal> reservaYAula , ReservaDTO reservaDTO) {
        try {
            // Llama al servicio para registrar la reserva
            reservaServicio.guardarReserva(reservaYAula, reservaDTO);

            // Si todo está bien, devuelve la lista de aulas y el reservaDTO
            return ResponseEntity.ok(new ErrorAlGuardar(true, null));
        } catch (ValidationException ex) {
            // En caso de error, devuelve el mensaje de error
            return ResponseEntity.badRequest().body(new ErrorAlGuardar(false, ex.getMessage()));
        }
    }
}
