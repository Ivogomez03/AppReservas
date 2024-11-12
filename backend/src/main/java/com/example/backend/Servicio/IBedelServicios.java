package com.example.backend.Servicio;

import com.example.backend.DTO.BedelDTO;
import com.example.backend.DTO.ValidarContrasenaDTO;

public interface IBedelServicios {
    public ValidarContrasenaDTO validarBedel(BedelDTO bedeldto);
}
