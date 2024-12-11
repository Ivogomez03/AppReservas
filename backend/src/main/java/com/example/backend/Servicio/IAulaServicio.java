package com.example.backend.Servicio;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.DTO.AulaDTO;
import com.example.backend.DTO.BuscarAulaDTO;
import com.example.backend.DTO.ModificarAulaDTO;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.DTO.SalidaCU9DTO;
import com.example.backend.Modelos.Aula;
import com.example.backend.Modelos.AulaInformatica;
import com.example.backend.Modelos.AulaMultimedio;
import com.example.backend.Modelos.AulaSinRecursosAdicionales;
public interface IAulaServicio {
    public Aula findByNumeroDeAula(int numeroDeAula);

    public String modificarAula(ModificarAulaDTO dto);

    public void modificarAulaInformatica(ModificarAulaDTO aulaDTO,AulaInformatica aula);

    public void modificarAulaMultimedio(ModificarAulaDTO aulaDTO,AulaMultimedio aula);

    public void modificarAulaSinRecursosAdicionales(ModificarAulaDTO aulaDTO,AulaSinRecursosAdicionales aula);

    @SuppressWarnings({"rawtypes" })
    public ArrayList buscarAulas(BuscarAulaDTO buscarAulaDTO);

    public SalidaCU9DTO convertirASalidaCU9DTO(Aula aula);
    
    public Aula crearAula(AulaDTO aulaDTO);

    public List<AulaDTO> obtenerAulasPorClase(Class<?> tipoClase);
    


    public AulaDTO convertirADTO(Aula aula);

    public Aula convertirAEntidad(AulaDTO dto);

    public Aula crearAula(ReservaDTO reserva);
}
