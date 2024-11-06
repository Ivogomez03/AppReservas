package com.example.backend.Repositorio;

import java.util.List;

import com.example.backend.DTO.BedelDTO;

public interface BedelDAO{
    public Boolean exiteBedelPorId(int idUsuario);
    public void guardarBedel(BedelDTO bedeldto);
    public List<BedelDTO> recuperarBedeles();
}
