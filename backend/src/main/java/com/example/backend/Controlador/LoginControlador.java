package com.example.backend.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.Servicio.Implementacion.LoginServicio;
import com.example.backend.DTO.LoginDTO;
import com.example.backend.DTO.SalidaLoginDTO;

@RestController
public class LoginControlador {

    @Autowired
    private LoginServicio loginServicio;

    @PostMapping("/login")
    public ResponseEntity<SalidaLoginDTO> validarLogin(@RequestBody LoginDTO loginDTO) {
        // Llamar al servicio para validar usuario
        int esValido = loginServicio.validarUsuario(loginDTO);

        // Crear la respuesta basada en la validación
        SalidaLoginDTO salida;
        HttpStatus status;

        if (esValido == 1) {
            salida = new SalidaLoginDTO(true, false);
            status = HttpStatus.OK; // Usuario válido
        } else if (esValido == 2) {
            salida = new SalidaLoginDTO(false, true);
            status = HttpStatus.UNAUTHORIZED; // Contraseña incorrecta
        } else {
            salida = new SalidaLoginDTO(false, false);
            status = HttpStatus.NOT_FOUND; // Usuario no encontrado
        }

        // Retornar la respuesta con el código de estado HTTP
        return new ResponseEntity<>(salida, status);
    }
}