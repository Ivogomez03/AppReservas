package com.example.backend.Servicio;

import com.example.backend.DTO.BuscarAulaDTO;
import com.example.backend.DTO.SalidaCU9DTODTO;
import com.example.backend.DTO.AulaDTO;
import com.example.backend.Modelos.Aula;
public interface IAulaServicio {
    public List<SalidaCU9DTO> buscarAulas(BuscarAulaDTO buscarAulaDTO);
    public Aula crearAula(AulaDTO aulaDTO);
    public String modificarAula(ModificarAulaDTO dto);
}
