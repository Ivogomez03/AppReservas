
package com.example.backend.Logica.CU13.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://btmpjvhshoakcnfjp3jx-mysql.services.clever-cloud.com:3306/btmpjvhshoakcnfjp3jx"; // Cambia por tu DB
    private static final String USER = "u0hfjwm4nwv7ar8d";  // Usuario de MySQL
    private static final String PASSWORD = "xvuJRMO2VUcqsJiizViG";  // Contraseña de MySQL

    @SuppressWarnings("CallToPrintStackTrace")
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}