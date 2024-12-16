package com.example.backend.Servicio;

import java.util.List;

import com.example.backend.DTO.BedelDTO;
import com.example.backend.DTO.ValidarContrasenaDTO;
import com.example.backend.Modelos.Bedel;
import com.example.backend.Modelos.TurnoDeTrabajo;

public interface IBedelServicios {
    public ValidarContrasenaDTO validarBedel(BedelDTO bedeldto);
    public String validatePassword(String contra);
    public String existeBedelPorId(int id);
    public Bedel buscarBedel(int id);
    public boolean validarBedel(int id, String contrasena);
    public void eliminarBedel(BedelDTO bedelSeleccionado);
    public List<BedelDTO> buscarBedelesPorTurnoyApellido(TurnoDeTrabajo turno, String apellido);
    public BedelDTO convertirABedelDTO(Bedel bedel);
    public String modificarBedel(BedelDTO bedelDTO);
    public BedelDTO obtenerDatosBedel(BedelDTO bedelDTO);
}
