package com.example.backend.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.Servicio.ILoginServicio;
import com.example.backend.DTO.LoginDTO;

import java.util.Map;

@RestController
public class LoginControlador {

    @Autowired
    private ILoginServicio loginServicio;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        int result = loginServicio.validarUsuario(loginDTO);
        if (result == 1) {
            return ResponseEntity.ok(Map.of("admin", true, "idUsuario", loginDTO.getIdUsuario()));
        } else if (result == 2) {
            return ResponseEntity.ok(Map.of("bedel", true, "idUsuario", loginDTO.getIdUsuario()));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}