package com.example.backend.Controlador;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.DTO.ApiResponse;
import com.example.backend.DTO.AulaDTO;
import com.example.backend.DTO.AulaConHorariosDTO;
import com.example.backend.DTO.CDU01ReservaYAulaFinal;
import com.example.backend.DTO.CDU01ReservasYAulas;
import com.example.backend.DTO.ErrorAlGuardar;
import com.example.backend.DTO.ObjetoFinalCU1;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Excepciones.ValidationException;
import com.example.backend.Servicio.IReservaServicio;

@RestController
public class ReservaControlador {

    @Autowired
    private IReservaServicio reservaServicio;

    @GetMapping("/registrar")
    public ResponseEntity<?> mostrarPaginaRegistro() {
        // Puedes devolver un modelo, una vista, o simplemente un ResponseEntity
        // Dependiendo de tu arquitectura (MVC, API REST, etc.)
        return ResponseEntity.ok("Página de registro");
    }

    @PostMapping("/reserva/registrar")
    public ResponseEntity<ApiResponse<?>> registrarReserva(@RequestBody ReservaDTO reservaDTO)
            throws ClassNotFoundException {
        System.out.println("Received idBedel: " + reservaDTO.getIdBedel()); // Log the idBedel
        try {
            // Llama al servicio para registrar la reserva
            List<CDU01ReservasYAulas> aulasDisponibles = reservaServicio.registrarReserva(reservaDTO);
            boolean esSuperpuesto = false;
            for (CDU01ReservasYAulas reserva : aulasDisponibles) {
                for (AulaDTO aula : reserva.getAulas()) { // Ahora accedemos a getAulas() de cada reserva
                    if (aula instanceof AulaConHorariosDTO) {

                        esSuperpuesto = true;
                    }
                }
            }
            // Si todo está bien, devuelve la lista de aulas y el reservaDTO
            return ResponseEntity
                    .ok(new ApiResponse<>(true,
                            Map.of("aulas", aulasDisponibles, "reserva", reservaDTO, "superpuesto", esSuperpuesto),
                            null));
        } catch (ValidationException ex) {
            // En caso de error, devuelve el mensaje de error
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, null, ex.getMessage()));
        }
    }

    @PostMapping("/reserva/guardar")
    public ResponseEntity<ErrorAlGuardar> guardarReserva(@RequestBody ObjetoFinalCU1 objetoFinal) {
        System.out.println("Received idBedel: " + objetoFinal.getReserva().getIdBedel()); // Log the idBedel
        try {

            // Llama al servicio para registrar la reserva
            reservaServicio.guardarReserva(objetoFinal.getReservaYAula(), objetoFinal.getReserva());

            // Si todo está bien, devuelve la lista de aulas y el reservaDTO
            return ResponseEntity.ok(new ErrorAlGuardar(true, null));
        } catch (ValidationException ex) {
            // En caso de error, devuelve el mensaje de error
            return ResponseEntity.badRequest().body(new ErrorAlGuardar(false, ex.getMessage()));
        }
    }
}
