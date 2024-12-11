package com.example.backend.Servicio;

import com.example.backend.DTO.BuscarAulaDTO;
import com.example.backend.DTO.HorarioSuperpuestoDTO;
import com.example.backend.DTO.ModificarAulaDTO;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.DTO.SalidaCU9DTO;
import com.example.backend.DTO.AulaConHorariosDTO;
import com.example.backend.DTO.AulaDTO;
import com.example.backend.Modelos.Aula;
import com.example.backend.Modelos.AulaInformatica;
import com.example.backend.Modelos.AulaMultimedio;
import com.example.backend.Modelos.AulaSinRecursosAdicionales;
import com.example.backend.Modelos.DiaSemana;
import com.example.backend.Modelos.Esporadica;
import com.example.backend.Modelos.Periodica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
    
    public List<AulaDTO> obtenerAulasDisponiblesPeriodicasConPeriodo(Class<?> tipoClase, int idPeriodo, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin, int capacidadMinima);

    public AulaConHorariosDTO obtenerAulaConMenorSuperposicionPeriodica(Class<?> tipoClase, List<Periodica> reservas, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin, int capacidadMinima);

    public List<AulaDTO> obtenerAulasDisponiblesEsporadicas(Class<?> tipoClase, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, int capacidadMinima);

    public AulaConHorariosDTO obtenerAulaConMenorSuperposicionEsporadica(Class<?> tipoClase, List<Esporadica> reservas, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, int capacidadMinima);

    public AulaConHorariosDTO convertirAEntidadConHorarios(Aula aula, HorarioSuperpuestoDTO horarioSuperpuesto);

    public AulaDTO convertirADTO(Aula aula);

    public Aula convertirAEntidad(AulaDTO dto);

    public Aula crearAula(ReservaDTO reserva);
}
