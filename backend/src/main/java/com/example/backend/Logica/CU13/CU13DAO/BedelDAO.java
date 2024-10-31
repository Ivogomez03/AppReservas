package com.example.backend.Logica.CU13.CU13DAO;
import java.util.List;
import com.example.backend.Logica.CU13.Bedel.BedelDTO;
public interface BedelDAO {
    void guardarBedel(BedelDTO bedelDTO);
    boolean existeBedelPorID(int idUsuario);
    List<BedelDTO> recuperarBedeles();
}
