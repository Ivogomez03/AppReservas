package com.example.backend.Logica.CU13.BD;
import java.sql.Connection;
import java.sql.SQLException;

import com.example.backend.Logica.CU13.Bedel.BedelDTO;
import com.example.backend.Logica.CU13.Bedel.TurnoDeTrabajo;
import com.example.backend.Logica.CU13.CU13DAO.BedelDAO;
public class PruebaConexion {
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        BedelDTO nuevoBedel = new BedelDTO(
                "Carlos", 
                "González", 
                143,  // ID_usuario
                "password123", 
                TurnoDeTrabajo.Tarde, 
                1  // ID_admin_creador (clave foránea)
        );

        // Llamamos a los métodos del DAO para verificar y guardar
         try (Connection conn = ConexionDB.getConnection();
             BedelDAO bedelDAO = new BedelDAO(conn)) {

            if (bedelDAO.existeBedel(nuevoBedel.getIdUsuario())) {
                System.out.println("El ID del Bedel ya existe en la base de datos.");
            } else {
                bedelDAO.guardarBedel(nuevoBedel);
                System.out.println("El Bedel fue creado exitosamente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}