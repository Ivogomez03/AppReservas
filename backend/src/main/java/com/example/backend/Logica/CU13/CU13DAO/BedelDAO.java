package com.example.backend.Logica.CU13.CU13DAO;
import java.sql.SQLException;

import com.example.backend.Logica.CU13.Bedel.BedelDTO;

public interface BedelDAO{

    public boolean existeBedel(int idUsuario) throws SQLException;

    public void guardarBedel(BedelDTO bedelDTO) throws SQLException;

    public void close() throws SQLException;
    
}
