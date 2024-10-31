package com.example.backend.Logica.CU13.Bedel;
import com.example.backend.Logica.CU13.CU13DAO.BedelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bedel")
public class gestorBedel {

    @Autowired
    private BedelDAO bedelDAO;
   
    @PostMapping("/crear")
    public String crearBedel(@RequestBody BedelDTO bedelDTO) {
        if (bedelDAO.existeBedelPorID(bedelDTO.getIdUsuario())) {
            return "El ID del Bedel ya existe.";
        } else {
            bedelDAO.guardarBedel(bedelDTO);
            return "Bedel creado exitosamente.";
        }
    }

    @GetMapping("/listar")
    public List<BedelDTO> listarBedeles() {
        return bedelDAO.recuperarBedeles();
    }
}