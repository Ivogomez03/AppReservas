package com.example.backend.Servicio;

import com.example.backend.DTO.BedelDTO;
import com.example.backend.DTO.ValidarContrasenaDTO;

public interface IBedelServicios {
    public ValidarContrasenaDTO validarBedel(BedelDTO bedeldto);
    public String modificarBedel(BedelDTO bedeldto);
    public BedelDTO obtenerBedel(int id);
    public String eliminarBedel(int id);
}
