package com.example.backend.Logica.CU13;

import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/bedel")
public class gestorBedel {

    @PostMapping("/crear")
    @SuppressWarnings("CallToPrintStackTrace")
    public String crearBedel(@RequestBody BedelDTO bedelDTO) {
        try (Connection conn = ConexionDB.getConnection()) {
            BedelDAO bedelDAO = new BedelDAO(conn);

            if (bedelDAO.existeBedel(bedelDTO.getIdUsuario())) {
                return "El ID del Bedel ya existe.";
            } else {
                bedelDAO.guardarBedel(bedelDTO);
                return "Bedel creado exitosamente.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al crear el Bedel.";
        }
    }
}