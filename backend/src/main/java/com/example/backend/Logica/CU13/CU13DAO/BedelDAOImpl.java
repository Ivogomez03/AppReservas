package com.example.backend.Logica.CU13.CU13DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.backend.Logica.CU13.Bedel.BedelDTO;

public class BedelDAOImpl implements BedelDAO, AutoCloseable{

    private final Connection conn;
    
    @Override
     public boolean existeBedel(int idUsuario) throws SQLException {
        String sql = "SELECT 1 FROM Bedel WHERE ID_usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public BedelDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void guardarBedel(BedelDTO bedelDTO) throws SQLException {
        String sql = "INSERT INTO Bedel (ID_usuario, Nombre, Apellido, Turno_de_trabajo, ID_admin_creador, Contrasena) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bedelDTO.getIdUsuario());
            stmt.setString(2, bedelDTO.getNombre());
            stmt.setString(3, bedelDTO.getApellido());
            stmt.setString(4, bedelDTO.getTurnoDeTrabajo().name());
            stmt.setInt(5, bedelDTO.getID_admin_creador());
            stmt.setString(6, bedelDTO.getContrasena());

            stmt.executeUpdate();
        }
    }

    @SuppressWarnings("override")
     public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

}
