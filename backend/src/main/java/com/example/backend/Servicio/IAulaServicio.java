package com.example.backend.Servicio;

import com.example.backend.DTO.BuscarAulaDTO;
import com.example.backend.DTO.SalidaCU9DTODTO;

public interface IAulaServicio {
    public List<SalidaCU9DTO> buscarAulas(BuscarAulaDTO buscarAulaDTO);
}
