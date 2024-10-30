package com.example.backend.Logica.CU13;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BedelDAO implements AutoCloseable {
    private final Connection conn;

    public BedelDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean existeBedel(int idUsuario) throws SQLException {
        String sql = "SELECT 1 FROM Bedel WHERE ID_usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void guardarBedel(BedelDTO bedelDTO) throws SQLException {
        String sql = "INSERT INTO Bedel (ID_usuario, Nombre, Apellido, Turno_de_trabajo, ID_admin_creador) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bedelDTO.getIdUsuario());
            stmt.setString(2, bedelDTO.getNombre());
            stmt.setString(3, bedelDTO.getApellido());
            stmt.setString(4, bedelDTO.getTurnoDeTrabajo().name());
            stmt.setInt(5, bedelDTO.getIDAdminCreador());

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
