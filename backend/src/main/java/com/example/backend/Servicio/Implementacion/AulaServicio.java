package com.example.backend.Servicio.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;


import com.example.backend.DTO.BuscarAulaDTO;
import com.example.backend.DTO.SalidaCU9DTO;
import com.example.backend.Modelos.Aula;
import com.example.backend.Modelos.AulaInformatica;
import com.example.backend.Modelos.AulaMultimedio;
import com.example.backend.Modelos.AulaSinRecursosAdicionales;
import com.example.backend.Repositorio.AulaInformaticaDAO;
import com.example.backend.Repositorio.AulaMultimedioDAO;
import com.example.backend.Repositorio.AulaSRADAO;
import com.example.backend.Repositorio.ReservaDAO;
import com.example.backend.DTO.AulaDTO;
import com.example.backend.Excepciones.ValidationException;
import com.example.backend.Modelos.Dia;
import com.example.backend.Modelos.DiaSemana;
import com.example.backend.Modelos.Esporadica;
import com.example.backend.Modelos.FechaEspecifica;
import com.example.backend.Modelos.Periodica;
import com.example.backend.Modelos.Reserva;
import com.example.backend.Repositorio.AulaDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;




public class AulaServicio implements IAulaServicio {

    @Autowired
    private AulaInformaticaDAO aulaInformaticaDAO;
    private AulaSRADAO aulaSinRecursosAdicionalesDAO;
    private AulaMultimedioDAO aulaMultimedioDAO;

    @Autowired
    private AulaServicio aulaServicio;
    @Autowired 
    private AulaDAO aulaDAO;
    @Autowired 
    private ReservaDAO reservaDAO;

    public List<SalidaCU9DTO> buscarAulas(BuscarAulaDTO buscarAulaDTO) {
        List<Aula> aulas = new ArrayList<>();

        // Consultar cada DAO según el criterio
        if (buscarAulaDTO.getTipoAula() == null || buscarAulaDTO.getTipoAula().equalsIgnoreCase("Informatica")) {
            aulas.addAll(aulaInformaticaDAO.buscarAulasPorCriterio(buscarAulaDTO));
        }
        if (buscarAulaDTO.getTipoAula() == null || buscarAulaDTO.getTipoAula().equalsIgnoreCase("Multimedio")) {
            aulas.addAll(aulaMultimedioDAO.buscarAulasPorCriterio(buscarAulaDTO));
        }
        if (buscarAulaDTO.getTipoAula() == null || buscarAulaDTO.getTipoAula().equalsIgnoreCase("SinRecursosAdicionales")) {
            aulas.addAll(aulaSinRecursosAdicionalesDAO.buscarAulasPorCriterio(buscarAulaDTO));
        }
        if (aulas.isEmpty()){
            throw new ValidationException("No se encontro ningún aula con los criterios especificados");
        }

        return aulas.stream()
                .map(this::convertirASalidaCU9DTO)
                .collect(Collectors.toList());
        
    }
    private SalidaCU9DTO convertirASalidaCU9DTO(Aula aula) {
        SalidaCU9DTO dto = new SalidaCU9DTO();
        dto.setNroAula(aula.getNroAula());
        dto.setCapacidad(aula.getCapacidad());
        dto.setTipoAula(aula.getTipoAula());
        dto.setPiso(aula.getPiso());
        dto.setHabilitado(aula.getHabilitado());
        return dto;
    }
    
    public Aula crearAula(AulaDTO aulaDTO) {

        if(aulaDTO.isAulaMultimedia()){
            AulaMultimedio aula = new AulaMultimedio();
            aula.setTelevisor(aulaDTO.isTelevisor());
            aula.setProyector(aulaDTO.isProyector());
            aula.setComputadora(aulaDTO.isComputadora());
            aula.setVentilador(aulaDTO.isVentilador());

            aula.setTipoPizarron(aulaDTO.getTipoPizarron());
            aula.setNumeroDeAula(aulaDTO.getNumeroDeAula());
            aula.setCapacidad(aulaDTO.getCapacidad());
            aula.setPiso(aulaDTO.getPiso());
            aula.setIdAula(aulaDTO.getIdAula());
            aula.setCaracteristicas(aulaDTO.getCaracteristicas());
            aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());
            aula.setHabilitado(aulaDTO.isHabilitado());

            return aula;
        }
        else if(aulaDTO.isAulaInformatica()){
            AulaInformatica aula = new AulaInformatica();
            aula.setCantidadDeComputadoras(aulaDTO.getCantidadDeComputadoras());
            aula.setProyector(aulaDTO.isProyector());
            
            aula.setTipoPizarron(aulaDTO.getTipoPizarron());
            aula.setNumeroDeAula(aulaDTO.getNumeroDeAula());
            aula.setCapacidad(aulaDTO.getCapacidad());
            aula.setPiso(aulaDTO.getPiso());
            aula.setIdAula(aulaDTO.getIdAula());
            aula.setCaracteristicas(aulaDTO.getCaracteristicas());
            aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());
            aula.setHabilitado(aulaDTO.isHabilitado());

            return aula;
        }
        else if(aulaDTO.isAulaSinRecursosAdicionales()){
            AulaSinRecursosAdicionales aula = new AulaSinRecursosAdicionales();
            aula.setVentilador(aulaDTO.isVentilador());

            aula.setTipoPizarron(aulaDTO.getTipoPizarron());
            aula.setNumeroDeAula(aulaDTO.getNumeroDeAula());
            aula.setCapacidad(aulaDTO.getCapacidad());
            aula.setPiso(aulaDTO.getPiso());
            aula.setIdAula(aulaDTO.getIdAula());
            aula.setCaracteristicas(aulaDTO.getCaracteristicas());
            aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());
            aula.setHabilitado(aulaDTO.isHabilitado());

            return aula;
        }
        else{
            throw new ValidationException("Hubo un error con el tipo de reserva");
        }
    }
    public List<Aula> obtenerAulasPorClase(Class<? extends Aula> tipoClase) { 
if (tipoClase.equals(AulaMultimedio.class)) { 
return aulaDAO.findAll().stream().filter(aula -> aula instanceof AulaMultimedio).collect(Collectors.toList()); 
} else if (tipoClase.equals(AulaSinRecursosAdicionales.class)) { 
return aulaDAO.findAll().stream().filter(aula -> aula instanceof AulaSinRecursosAdicionales).collect(Collectors.toList()); 
} else if (tipoClase.equals(AulaInformatica.class)) { 
return aulaDAO.findAll().stream().filter(aula -> aula instanceof AulaInformatica).collect(Collectors.toList()); 
} throw new IllegalArgumentException("Tipo de aula no soportado: " + tipoClase.getName());
    }
    public List<Aula> obtenerAulasDisponiblesPeriodicasConPeriodo(Class<? extends Aula> tipoClase, int periodo, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin) { 
// Obtener todas las aulas por el tipo especificado 
List<Aula> aulasPorTipo = obtenerAulasPorClase(tipoClase); // Filtrar las aulas ocupadas en el mismo periodo 
List<Periodica> reservasEnPeriodo = reservaDAO.obtenerReservasPorPeriodo(periodo);
        List<Aula> aulasOcupadas = reservasEnPeriodo.stream()
                .flatMap(reserva -> reserva.getDias().stream()) // Obtener todos los días de cada reserva
                .filter(dia -> dia.getDiaSemana().equals(diaSemana)
                        && dia.getHoraInicio().isBefore(horaFin)
                        && dia.getHoraFin().isAfter(horaInicio))
                .map(Dia::getAula)
                .distinct()
                .collect(Collectors.toList());

        // Obtener aulas disponibles
        List<Aula> aulasDisponibles = aulasPorTipo.stream()
                .filter(aula -> !aulasOcupadas.contains(aula))
                .collect(Collectors.toList());

        // Si no hay aulas disponibles, obtener el aula con menor superposición
        if (aulasDisponibles.isEmpty()) {
             Aula aulaConMenorSuperposicion = obtenerAulaConMenorSuperposicionPeriodica(tipoClase, reservasEnPeriodo, diaSemana, horaInicio, horaFin);
            if (aulaConMenorSuperposicion != null) {
                aulasDisponibles.add(aulaConMenorSuperposicion);
            }
        }

        return aulasDisponibles;
    }
public List<Aula> obtenerAulasDisponiblesEsporadicas(Class<? extends Aula> tipoClase, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
    // Obtener todas las aulas por el tipo especificado
    List<Aula> aulasPorTipo = obtenerAulasPorClase(tipoClase);

    // Filtrar las aulas ocupadas en la misma fecha y horario
    List<Esporadica> reservasEnFecha = reservaDAO.obtenerReservasPorFecha(fecha);
    List<Aula> aulasOcupadas = reservasEnFecha.stream()
            .map(Esporadica::getFechaEspecifica)
            .filter(fechaEspecifica -> fechaEspecifica.getFecha().equals(fecha)
                    && fechaEspecifica.getHoraInicio().isBefore(horaFin)
                    && fechaEspecifica.getHoraFin().isAfter(horaInicio))
            .map(FechaEspecifica::getAula)
            .distinct()
            .collect(Collectors.toList());

    // Obtener aulas disponibles
    List<Aula> aulasDisponibles = aulasPorTipo.stream()
            .filter(aula -> !aulasOcupadas.contains(aula))
            .collect(Collectors.toList());

    // Si no hay aulas disponibles, obtener el aula con menor superposición
    if (aulasDisponibles.isEmpty()) {
        Aula aulaConMenorSuperposicion = obtenerAulaConMenorSuperposicionEsporadica(tipoClase, reservasEnFecha, fecha, horaInicio,horaFin);
        if (aulaConMenorSuperposicion != null) {
            aulasDisponibles.add(aulaConMenorSuperposicion);
        }
    }

    return aulasDisponibles;
}


public Aula obtenerAulaConMenorSuperposicionPeriodica(Class<? extends Aula> tipoClase, List<Periodica> reservas, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin) {
    // Obtener todas las aulas por el tipo especificado
    List<Aula> aulasPorTipo = obtenerAulasPorClase(tipoClase);

    // Calcular la superposición de cada aula
    Map<Aula, Long> superposiciones = reservas.stream()
            .flatMap(reserva -> reserva.getDias().stream())
            .filter(dia -> dia.getDiaSemana().equals(diaSemana)
                    && dia.getHoraInicio().isBefore(horaFin)
                    && dia.getHoraFin().isAfter(horaInicio))
            .collect(Collectors.groupingBy(Dia::getAula, Collectors.counting()));

    // Encontrar el aula con menor superposición
    return aulasPorTipo.stream()
            .min(Comparator.comparing(aula -> superposiciones.getOrDefault(aula, 0L)))
            .orElse(null);
}
public Aula obtenerAulaConMenorSuperposicionEsporadica(Class<? extends Aula> tipoClase, List<Esporadica> reservas, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
    // Obtener todas las aulas por el tipo especificado
    List<Aula> aulasPorTipo = obtenerAulasPorClase(tipoClase);

    // Calcular la superposición de cada aula
    Map<Aula, Long> superposiciones = reservas.stream()
            .map(Esporadica::getFechaEspecifica)
            .filter(fechaEspecifica -> fechaEspecifica.getFecha().equals(fecha)
                    && fechaEspecifica.getHoraInicio().isBefore(horaFin)
                    && fechaEspecifica.getHoraFin().isAfter(horaInicio))
            .collect(Collectors.groupingBy(FechaEspecifica::getAula, Collectors.counting()));

    // Encontrar el aula con menor superposición
    return aulasPorTipo.stream()
            .min(Comparator.comparing(aula -> superposiciones.getOrDefault(aula, 0L)))
            .orElse(null);
}

}

