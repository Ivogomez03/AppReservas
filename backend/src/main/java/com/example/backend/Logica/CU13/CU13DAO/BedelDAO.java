package com.example.backend.Logica.CU13.CU13DAO;
import java.sql.SQLException;

import com.example.backend.Logica.CU13.Bedel.Bedel;

public interface BedelDAO{

    public boolean existeBedel(int idUsuario) throws SQLException;

    public void guardarBedel(Bedel bedel) throws SQLException;

    public void close() throws SQLException;
    
}
