package com.example.backend.Repositorio;

import com.example.backend.DTO.BedelDTO;

public interface BedelDAO{
    public Boolean exiteBedelPorId(int idUsuario);
    public void guardarBedel(BedelDTO bedeldto);
}
