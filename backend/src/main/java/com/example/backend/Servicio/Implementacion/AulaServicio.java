package com.example.backend.Servicio.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.DTO.BuscarAulaDTO;
import com.example.backend.DTO.ModificarAulaDTO;
import com.example.backend.DTO.SalidaCU9DTO;
import com.example.backend.Modelos.Aula;
import com.example.backend.Modelos.AulaInformatica;
import com.example.backend.Modelos.AulaMultimedio;
import com.example.backend.Modelos.AulaSinRecursosAdicionales;
import com.example.backend.Repositorio.AulaInformaticaDAO;
import com.example.backend.Repositorio.AulaMultimedioDAO;
import com.example.backend.Repositorio.AulaSRADAO;
import com.example.backend.Repositorio.ReservaDAO;
import com.example.backend.Servicio.IAulaServicio;
import com.example.backend.DTO.AulaDTO;
import com.example.backend.Excepciones.ValidationException;
import com.example.backend.Modelos.Dia;
import com.example.backend.Modelos.DiaSemana;
import com.example.backend.Modelos.Esporadica;
import com.example.backend.Modelos.FechaEspecifica;
import com.example.backend.Modelos.Periodica;
import com.example.backend.Repositorio.AulaDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@Service
public class AulaServicio implements IAulaServicio {

    @Autowired
    private  AulaInformaticaDAO aulaInformaticaDAO;
    private  AulaSRADAO aulaSRADAO;
    private  AulaMultimedioDAO aulaMultimedioDAO;

    public Aula findByNumeroDeAula(int numeroDeAula) {
        return aulaDAO.findByNumeroDeAula(numeroDeAula);
    }

    public String modificarAula(ModificarAulaDTO dto){

        Aula aula = findByNumeroDeAula(dto.getNumeroDeAula());
        
        // Validación del tipo de aula
        if (aula == null) {
            throw new IllegalArgumentException("Este numero de aula no existe");
            
        }
        else if(dto.getCapacidad() < 0){
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        else{
        
            
                if (aula instanceof AulaInformatica) {
                    modificarAulaInformatica(dto,(AulaInformatica)aula);
                } 
                else if (aula instanceof AulaMultimedio) {
                    modificarAulaMultimedio(dto,(AulaMultimedio)aula);
                } 
                else if (aula instanceof AulaSinRecursosAdicionales) {
                    modificarAulaSinRecursosAdicionales(dto,(AulaSinRecursosAdicionales)aula);
                }
                return "El aula ha sido modificada correctamente";
            
        }
      
        
    }
    public void modificarAulaInformatica(ModificarAulaDTO aulaDTO,AulaInformatica aula){
        

        if (aulaDTO.getCantidadDeComputadoras() <= 0) {
            throw new IllegalArgumentException("La cantidad de PCs debe ser mayor a cero");
        }

        if (aulaDTO.getCantidadDeComputadoras() > aulaDTO.getCapacidad()) {
            throw new IllegalArgumentException("La cantidad de PCs no puede ser mayor a la capacidad");
        }

        // 4. Modificar los datos del aula informática
        aula.setCapacidad(aulaDTO.getCapacidad());
        aula.setCantidadDeComputadoras(aulaDTO.getCantidadDeComputadoras());
        aula.setTipoPizarron(aulaDTO.getTipoPizarron());
        aula.setCanion(aulaDTO.isCanion());
        aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());

        // 5. Guardar los cambios en la base de datos
        aulaInformaticaDAO.save(aula);


    }

    public void modificarAulaMultimedio(ModificarAulaDTO aulaDTO,AulaMultimedio aula){

        // 4. Modificar los datos del aula multimedio
        aula.setCapacidad(aulaDTO.getCapacidad());
        aula.setTipoPizarron(aulaDTO.getTipoPizarron());
        aula.setCanion(aulaDTO.isCanion());
        aula.setTelevisor(aulaDTO.isTelevisor());
        aula.setVentilador(aulaDTO.isVentilador());
        aula.setComputadora(aulaDTO.isComputadora());
        aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());

        // 5. Guardar los cambios en la base de datos
        aulaMultimedioDAO.save(aula);
    }

    public void modificarAulaSinRecursosAdicionales(ModificarAulaDTO aulaDTO,AulaSinRecursosAdicionales aula){
        if (aulaDTO.isAireAcondicionado() && aulaDTO.isVentilador()) {
            throw new IllegalArgumentException("Si el aula posee aire acondicionado no puede poseer ventiladores y viceversa.");
        }
        // 4. Modificar los datos del aula sin recursos
        aula.setCapacidad(aulaDTO.getCapacidad());
        aula.setTipoPizarron(aulaDTO.getTipoPizarron());
  
        aula.setVentilador(aulaDTO.isVentilador());
        aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());

        // 5. Guardar los cambios en la base de datos
        aulaSRADAO.save(aula);


    }
    @Autowired 
    private AulaDAO aulaDAO;
    @Autowired 
    private ReservaDAO reservaDAO;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ArrayList buscarAulas(BuscarAulaDTO buscarAulaDTO) {
        ArrayList<Aula> aulas = new ArrayList<>();

        // Consultar cada DAO según el criterio
        if (buscarAulaDTO.getTipoAula() == null || buscarAulaDTO.getTipoAula().equalsIgnoreCase("Informatica")) {
            aulas.addAll(aulaInformaticaDAO.buscarPorCriterio(buscarAulaDTO.getNumeroDeAula(), buscarAulaDTO.getCapacidad()));
        }
        if (buscarAulaDTO.getTipoAula() == null || buscarAulaDTO.getTipoAula().equalsIgnoreCase("Multimedio")) {
            aulas.addAll(aulaInformaticaDAO.buscarPorCriterio(buscarAulaDTO.getNumeroDeAula(), buscarAulaDTO.getCapacidad()));
        }
        if (buscarAulaDTO.getTipoAula() == null || buscarAulaDTO.getTipoAula().equalsIgnoreCase("SinRecursosAdicionales")) {
            aulas.addAll(aulaInformaticaDAO.buscarPorCriterio(buscarAulaDTO.getNumeroDeAula(), buscarAulaDTO.getCapacidad()));
        }
        if (aulas.isEmpty()){
            throw new ValidationException("No se encontro ningún aula con los criterios especificados");
        }

        return (ArrayList) aulas.stream()
                .map(this::convertirASalidaCU9DTO)
                .collect(Collectors.toList());
        
    }
    private SalidaCU9DTO convertirASalidaCU9DTO(Aula aula) {
        SalidaCU9DTO dto = new SalidaCU9DTO();
        dto.setNumeroDeAula(aula.getNumeroDeAula());
        dto.setCapacidad(aula.getCapacidad());
        dto.setTipoAula(aula.getTipoAula());
        dto.setPiso(aula.getPiso());
        dto.setHabilitado(aula.isHabilitado());
        return dto;
    }
    
    public Aula crearAula(AulaDTO aulaDTO) {

        if(aulaDTO.isAulaMultimedia()){
            AulaMultimedio aula = new AulaMultimedio();
            aula.setTelevisor(aulaDTO.isTelevisor());
            aula.setCanion(aulaDTO.isCanion());
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
            aula.setCanion(aulaDTO.isCanion());
            
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
   public List<AulaDTO> obtenerAulasPorClase(Class<? extends Aula> tipoClase) {
    return aulaDAO.findAll().stream()
            .filter(tipoClase::isInstance)
            .map(this::convertirADTO)
            .collect(Collectors.toList());
}

 public List<AulaDTO> obtenerAulasDisponiblesPeriodicasConPeriodo(Class<? extends Aula> tipoClase, int periodo, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin) {
    List<AulaDTO> aulasPorTipo = obtenerAulasPorClase(tipoClase);

    List<Periodica> reservasEnPeriodo = reservaDAO.obtenerReservasPorPeriodo(periodo);
    List<Aula> aulasOcupadas = reservasEnPeriodo.stream()
            .flatMap(reserva -> reserva.getDias().stream())
            .filter(dia -> dia.getDiaSemana().equals(diaSemana)
                    && dia.getHoraInicio().isBefore(horaFin)
                    && dia.getHoraFin().isAfter(horaInicio))
            .map(Dia::getAula)
            .distinct()
            .collect(Collectors.toList());

    List<AulaDTO> aulasDisponibles = aulasPorTipo.stream()
            .filter(aulaDTO -> aulasOcupadas.stream().noneMatch(aula -> aula.getIdAula() == aulaDTO.getIdAula()))
            .collect(Collectors.toList());

    if (aulasDisponibles.isEmpty()) {
        Aula aulaConMenorSuperposicion = obtenerAulaConMenorSuperposicionPeriodica(tipoClase, reservasEnPeriodo, diaSemana, horaInicio, horaFin);
        if (aulaConMenorSuperposicion != null) {
            aulasDisponibles.add(convertirADTO(aulaConMenorSuperposicion));
        }
    }

    return aulasDisponibles;
}


 public List<AulaDTO> obtenerAulasDisponiblesEsporadicas(Class<? extends Aula> tipoClase, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
    List<AulaDTO> aulasPorTipo = obtenerAulasPorClase(tipoClase);

    // Usar `idReserva` en lugar de `idPeriodica`
    List<Esporadica> reservasEnFecha = reservaDAO.obtenerReservasPorFecha(fecha);
    List<Aula> aulasOcupadas = reservasEnFecha.stream()
            .map(Esporadica::getFechaEspecifica)
            .filter(fechaEspecifica -> fechaEspecifica.getFecha().equals(fecha)
                    && fechaEspecifica.getHoraInicio().isBefore(horaFin)
                    && fechaEspecifica.getHoraFin().isAfter(horaInicio))
            .map(FechaEspecifica::getAula)
            .distinct()
            .collect(Collectors.toList());

    List<AulaDTO> aulasDisponibles = aulasPorTipo.stream()
            .filter(aulaDTO -> aulasOcupadas.stream().noneMatch(aula -> aula.getIdAula() == aulaDTO.getIdAula()))
            .collect(Collectors.toList());

    if (aulasDisponibles.isEmpty()) {
        Aula aulaConMenorSuperposicion = obtenerAulaConMenorSuperposicionEsporadica(tipoClase, reservasEnFecha, fecha, horaInicio, horaFin);
        if (aulaConMenorSuperposicion != null) {
            aulasDisponibles.add(convertirADTO(aulaConMenorSuperposicion));
        }
    }

    return aulasDisponibles;
}
public Aula obtenerAulaConMenorSuperposicionPeriodica(Class<? extends Aula> tipoClase, List<Periodica> reservas, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin) {
    List<Aula> aulasPorTipo = obtenerAulasPorClase(tipoClase).stream().map(this::convertirAEntidad).collect(Collectors.toList());

    Map<Aula, Long> superposiciones = reservas.stream()
            .flatMap(reserva -> reserva.getDias().stream())
            .filter(dia -> dia.getDiaSemana().equals(diaSemana)
                    && dia.getHoraInicio().isBefore(horaFin)
                    && dia.getHoraFin().isAfter(horaInicio))
            .collect(Collectors.groupingBy(Dia::getAula, Collectors.counting()));

    return aulasPorTipo.stream()
            .min(Comparator.comparing(aula -> superposiciones.getOrDefault(aula, 0L)))
            .orElse(null);
}



public Aula obtenerAulaConMenorSuperposicionEsporadica(Class<? extends Aula> tipoClase, List<Esporadica> reservas, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
    List<Aula> aulasPorTipo = obtenerAulasPorClase(tipoClase).stream().map(this::convertirAEntidad).collect(Collectors.toList());

    Map<Aula, Long> superposiciones = reservas.stream()
            .map(Esporadica::getFechaEspecifica)
            .filter(fechaEspecifica -> fechaEspecifica.getFecha().equals(fecha)
                    && fechaEspecifica.getHoraInicio().isBefore(horaFin)
                    && fechaEspecifica.getHoraFin().isAfter(horaInicio))
            .collect(Collectors.groupingBy(FechaEspecifica::getAula, Collectors.counting()));

    return aulasPorTipo.stream()
            .min(Comparator.comparing(aula -> superposiciones.getOrDefault(aula, 0L)))
            .orElse(null);
}




   private AulaDTO convertirADTO(Aula aula) {
    AulaDTO dto = new AulaDTO();
    dto.setIdAula(aula.getIdAula());
    dto.setTipoPizarron(aula.getTipoPizarron());
    dto.setNumeroDeAula(aula.getNumeroDeAula());
    dto.setCapacidad(aula.getCapacidad());
    dto.setPiso(aula.getPiso());
    dto.setCaracteristicas(aula.getCaracteristicas());
    dto.setAireAcondicionado(aula.isAireAcondicionado());
    dto.setHabilitado(aula.isHabilitado());
    if (aula instanceof AulaInformatica) {
        AulaInformatica informatica = (AulaInformatica) aula;
        dto.setCantidadDeComputadoras(informatica.getCantidadDeComputadoras());
        dto.setCanion(informatica.isCanion());
        dto.setAulaInformatica(true);
    } else if (aula instanceof AulaSinRecursosAdicionales) {
        AulaSinRecursosAdicionales sinRecursos = (AulaSinRecursosAdicionales) aula;
        dto.setVentilador(sinRecursos.isVentilador());
        dto.setAulaSinRecursosAdicionales(true);
    } else if (aula instanceof AulaMultimedio) {
        AulaMultimedio multimedio = (AulaMultimedio) aula;
        dto.setTelevisor(multimedio.isTelevisor());
        dto.setCanion(multimedio.isCanion());
        dto.setComputadora(multimedio.isComputadora());
        dto.setVentilador(multimedio.isVentilador());
        dto.setAulaMultimedia(true);
    }
    return dto;
}
private Aula convertirAEntidad(AulaDTO dto) {
    Aula aula;
    if (dto.isAulaInformatica()) {
        AulaInformatica informatica = new AulaInformatica();
        informatica.setCantidadDeComputadoras(dto.getCantidadDeComputadoras());
        informatica.setCanion(dto.isCanion());
        aula = informatica;
    } else if (dto.isAulaSinRecursosAdicionales()) {
        AulaSinRecursosAdicionales sinRecursos = new AulaSinRecursosAdicionales();
        sinRecursos.setVentilador(dto.isVentilador());
        aula = sinRecursos;
    } else if (dto.isAulaMultimedia()) {
        AulaMultimedio multimedio = new AulaMultimedio();
        multimedio.setTelevisor(dto.isTelevisor());
        multimedio.setCanion(dto.isCanion());
        multimedio.setComputadora(dto.isComputadora());
        multimedio.setVentilador(dto.isVentilador());
        aula = multimedio;
    } else {
        throw new IllegalArgumentException("Tipo de aula no soportado en DTO: " + dto);
    }
    aula.setIdAula(dto.getIdAula());
    aula.setTipoPizarron(dto.getTipoPizarron());
    aula.setNumeroDeAula(dto.getNumeroDeAula());
    aula.setCapacidad(dto.getCapacidad());
    aula.setPiso(dto.getPiso());
    aula.setCaracteristicas(dto.getCaracteristicas());
    aula.setAireAcondicionado(dto.isAireAcondicionado());
    aula.setHabilitado(dto.isHabilitado());
    return aula;
}
}

