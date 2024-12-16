package com.example.backend.Servicio.Implementacion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.DTO.AulaConHorariosDTO;
import com.example.backend.DTO.AulaDTO;
import com.example.backend.DTO.BuscarAulaDTO;
import com.example.backend.DTO.HorarioSuperpuestoDTO;
import com.example.backend.DTO.ModificarAulaDTO;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.DTO.ReservaSuperpuestaDTO;
import com.example.backend.DTO.SalidaCU9DTO;
import com.example.backend.Excepciones.ValidationException;
import com.example.backend.Modelos.Aula;
import com.example.backend.Modelos.AulaInformatica;
import com.example.backend.Modelos.AulaMultimedio;
import com.example.backend.Modelos.AulaSinRecursosAdicionales;
import com.example.backend.Modelos.Dia;
import com.example.backend.Modelos.DiaSemana;
import com.example.backend.Modelos.Esporadica;
import com.example.backend.Modelos.FechaEspecifica;
import com.example.backend.Modelos.Periodica;
import com.example.backend.Modelos.Periodo;
import com.example.backend.Modelos.Reserva;
import com.example.backend.Repositorio.AulaDAO;
import com.example.backend.Repositorio.AulaInformaticaDAO;
import com.example.backend.Repositorio.AulaMultimedioDAO;
import com.example.backend.Repositorio.AulaSRADAO;
import com.example.backend.Repositorio.PeriodoDAO;
import com.example.backend.Repositorio.ReservaDAO;
import com.example.backend.Servicio.IAulaServicio;

@Service
public class AulaServicio implements IAulaServicio {

    @Autowired
    private AulaInformaticaDAO aulaInformaticaDAO;
    @Autowired
    private AulaSRADAO aulaSRADAO;
    @Autowired
    private AulaMultimedioDAO aulaMultimedioDAO;
    @Autowired
    private PeriodoDAO periodoDAO;
    @Autowired
    private AulaDAO aulaDAO;
    @Autowired
    private ReservaDAO reservaDAO;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AulaServicio.class);

    @Override
    public Aula findByNumeroDeAula(int numeroDeAula) {
        return aulaDAO.findByNumeroDeAula(numeroDeAula);
    }

    @Override
    public String modificarAula(ModificarAulaDTO dto) {

        Aula aula = findByNumeroDeAula(dto.getNumeroDeAula());

        // Validación del tipo de aula
        if (aula == null) {
            throw new IllegalArgumentException("Este numero de aula no existe");

        } else if (dto.getCapacidad() < 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        } else {

            if (aula instanceof AulaInformatica) {
                modificarAulaInformatica(dto, (AulaInformatica) aula);
            } else if (aula instanceof AulaMultimedio) {
                modificarAulaMultimedio(dto, (AulaMultimedio) aula);
            } else if (aula instanceof AulaSinRecursosAdicionales) {
                modificarAulaSinRecursosAdicionales(dto, (AulaSinRecursosAdicionales) aula);
            }
            return "El aula ha sido modificada correctamente";

        }

    }

    @Override
    public void modificarAulaInformatica(ModificarAulaDTO aulaDTO, AulaInformatica aula) {

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

    @Override
    public void modificarAulaMultimedio(ModificarAulaDTO aulaDTO, AulaMultimedio aula) {

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

    @Override
    public void modificarAulaSinRecursosAdicionales(ModificarAulaDTO aulaDTO, AulaSinRecursosAdicionales aula) {
        if (aulaDTO.isAireAcondicionado() && aulaDTO.isVentilador()) {
            throw new IllegalArgumentException(
                    "Si el aula posee aire acondicionado no puede poseer ventiladores y viceversa.");
        }
        // 4. Modificar los datos del aula sin recursos
        aula.setCapacidad(aulaDTO.getCapacidad());
        aula.setTipoPizarron(aulaDTO.getTipoPizarron());

        aula.setVentilador(aulaDTO.isVentilador());
        aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());

        // 5. Guardar los cambios en la base de datos
        aulaSRADAO.save(aula);

    }

    @Override
    @SuppressWarnings({ "rawtypes" })
    public ArrayList buscarAulas(BuscarAulaDTO buscarAulaDTO) {
        ArrayList<Aula> aulas = new ArrayList<>();
        System.out.println("El DTO es: " + buscarAulaDTO);
        // Consultar cada DAO según el criterio
        if (buscarAulaDTO.getTipoAula() == null || buscarAulaDTO.getTipoAula().equalsIgnoreCase("Todas")) {
            aulas.addAll(aulaDAO.buscarPorCriterio(buscarAulaDTO.getNumeroDeAula(), buscarAulaDTO.getCapacidad()));
        } else {
            if (buscarAulaDTO.getTipoAula().equalsIgnoreCase("Informatica")) {
                aulas.addAll(aulaInformaticaDAO.buscarPorCriterio(buscarAulaDTO.getNumeroDeAula(),
                        buscarAulaDTO.getCapacidad()));
            }
            if (buscarAulaDTO.getTipoAula().equalsIgnoreCase("Multimedio")) {
                aulas.addAll(aulaMultimedioDAO.buscarPorCriterio(buscarAulaDTO.getNumeroDeAula(),
                        buscarAulaDTO.getCapacidad()));
            }
            if (buscarAulaDTO.getTipoAula().equalsIgnoreCase("Sin Recursos Adicionales")) {
                aulas.addAll(
                        aulaSRADAO.buscarPorCriterio(buscarAulaDTO.getNumeroDeAula(), buscarAulaDTO.getCapacidad()));
            }
        }
        if (aulas.isEmpty()) {
            throw new ValidationException("No se encontro ningún aula con los criterios especificados");
        }

        return (ArrayList) aulas.stream()
                .map(this::convertirASalidaCU9DTO)
                .collect(Collectors.toList());

    }

    @Override
    public SalidaCU9DTO convertirASalidaCU9DTO(Aula aula) {
        SalidaCU9DTO dto = new SalidaCU9DTO();
        dto.setNumeroDeAula(aula.getNumeroDeAula());
        dto.setCapacidad(aula.getCapacidad());
        dto.setTipoAula(aula.getTipoAula());
        dto.setPiso(aula.getPiso());
        dto.setAireAcondicionado(aula.isAireAcondicionado());
        dto.setHabilitado(aula.isHabilitado());
        dto.setTipoPizarron(aula.getTipoPizarron());
        if (aula instanceof AulaMultimedio) {
            AulaMultimedio multimedio = (AulaMultimedio) aula;
            dto.setComputadora(multimedio.isComputadora());
            dto.setTelevisor(multimedio.isTelevisor());
            dto.setVentilador(multimedio.isVentilador());
            dto.setCanion(multimedio.isCanion());
        }
        if (aula instanceof AulaInformatica) {
            AulaInformatica informatica = (AulaInformatica) aula;
            dto.setCantidadDeComputadoras(informatica.getCantidadDeComputadoras());
            dto.setCanion(informatica.isCanion());
        }
        if (aula instanceof AulaSinRecursosAdicionales) {
            AulaSinRecursosAdicionales aulaSRA = (AulaSinRecursosAdicionales) aula;
            dto.setVentilador(aulaSRA.isVentilador());
        }
        return dto;
    }

    @Override
    public Aula crearAula(AulaDTO aulaDTO) {

        if (aulaDTO.isAulaMultimedia()) {
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

            return aula;
        } else if (aulaDTO.isAulaInformatica()) {
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

            return aula;
        } else if (aulaDTO.isAulaSinRecursosAdicionales()) {
            AulaSinRecursosAdicionales aula = new AulaSinRecursosAdicionales();
            aula.setVentilador(aulaDTO.isVentilador());

            aula.setTipoPizarron(aulaDTO.getTipoPizarron());
            aula.setNumeroDeAula(aulaDTO.getNumeroDeAula());
            aula.setCapacidad(aulaDTO.getCapacidad());
            aula.setPiso(aulaDTO.getPiso());
            aula.setIdAula(aulaDTO.getIdAula());
            aula.setCaracteristicas(aulaDTO.getCaracteristicas());
            aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());

            return aula;
        } else {
            throw new ValidationException("Hubo un error con el tipo de reserva");
        }
    }

    @Override
    public List<AulaDTO> obtenerAulasPorClase(Class<?> tipoClase) {
        return aulaDAO.findAll().stream()
                .filter(tipoClase::isInstance)
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<AulaDTO> obtenerAulasDisponiblesPeriodicas(Class<?> tipoClase, int idPeriodo, DiaSemana diaSemana,
            LocalTime horaInicio, LocalTime horaFin, int capacidadMinima) {
        List<AulaDTO> aulasPorTipo = obtenerAulasPorClase(tipoClase);
        System.out.println("TipoClase: " + tipoClase + " idPeriodo: " + idPeriodo + " DiaSemana: " + diaSemana
                + horaInicio + horaFin + capacidadMinima);
        // Obtener el periodo actual
        Periodo periodoActual = periodoDAO.findById(idPeriodo)
                .orElseThrow(() -> new IllegalArgumentException("Periodo no encontrado"));
        LocalDate fechaInicioPeriodo = periodoActual.getFechaInicio();
        LocalDate fechaFinPeriodo = periodoActual.getFechaFin();

        // Obtener reservas periódicas en el periodo especificado
        List<Periodica> reservasPeriodicas = reservaDAO.obtenerReservasPorFechasPeriodo(fechaInicioPeriodo,
                fechaFinPeriodo);
        List<Aula> aulasOcupadasPeriodicas = reservasPeriodicas.stream()
                .flatMap(reserva -> reserva.getDias().stream())
                .filter(dia -> dia.getDiaSemana().equals(diaSemana)
                        && dia.getHoraInicio().isBefore(horaFin)
                        && dia.getHoraFin().isAfter(horaInicio))
                .map(Dia::getAula)
                .distinct()
                .collect(Collectors.toList());

        // Obtener todas las reservas esporádicas
        List<Esporadica> reservasEsporadicas = reservaDAO.obtenerReservasEsporadicas();
        List<Aula> aulasOcupadasEsporadicas = reservasEsporadicas.stream()
                .flatMap(esporadica -> esporadica.getFechaEspecifica().stream())
                .filter(fechaEspecifica -> !fechaEspecifica.getFecha().isBefore(fechaInicioPeriodo)
                        && !fechaEspecifica.getFecha().isAfter(fechaFinPeriodo)
                        && diaSemana.equals(convertirADiaSemana(fechaEspecifica.getFecha().getDayOfWeek()))
                        && fechaEspecifica.getHoraInicio().isBefore(horaFin)
                        && fechaEspecifica.getHoraFin().isAfter(horaInicio))
                .map(FechaEspecifica::getAula)
                .distinct()
                .collect(Collectors.toList());

        // Combinar aulas ocupadas de reservas esporádicas y periódicas
        Set<Aula> aulasOcupadas = new HashSet<>(aulasOcupadasPeriodicas);
        aulasOcupadas.addAll(aulasOcupadasEsporadicas);

        // Filtrar aulas disponibles
        List<AulaDTO> aulasDisponibles = aulasPorTipo.stream()
                .filter(aulaDTO -> aulaDTO.getCapacidad() >= capacidadMinima)
                .filter(aulaDTO -> aulasOcupadas.stream().noneMatch(aula -> aula.getIdAula() == aulaDTO.getIdAula()))
                .collect(Collectors.toList());

        if (aulasDisponibles.isEmpty()) {
            logger.info("No hay aulas disponibles, buscando aula con menor superposición");
            AulaConHorariosDTO aulaConMenorSuperposicion = obtenerAulaConMenorSuperposicion(tipoClase,
                    reservasEsporadicas, reservasPeriodicas, diaSemana, horaInicio, horaFin, capacidadMinima);
            if (aulaConMenorSuperposicion != null) {
                aulasDisponibles.add(aulaConMenorSuperposicion);
            }
        }

        return aulasDisponibles;
    }

    public List<AulaDTO> obtenerAulasDisponiblesEsporadicas(Class<?> tipoClase, LocalDate fecha, LocalTime horaInicio,
            LocalTime horaFin, int capacidadMinima) {
        List<AulaDTO> aulasPorTipo = obtenerAulasPorClase(tipoClase);

        // Obtener reservas esporádicas en la fecha especificada
        List<Esporadica> reservasEsporadicas = reservaDAO.obtenerReservasPorFecha(fecha);
        List<Aula> aulasOcupadasEsporadicas = reservasEsporadicas.stream()
                .flatMap(esporadica -> esporadica.getFechaEspecifica().stream())
                .filter(fechaEspecifica -> fechaEspecifica.getFecha().equals(fecha)
                        && fechaEspecifica.getHoraInicio().isBefore(horaFin)
                        && fechaEspecifica.getHoraFin().isAfter(horaInicio))
                .map(FechaEspecifica::getAula)
                .distinct()
                .collect(Collectors.toList());

        // Obtener el periodo actual
        List<Periodo> periodos = periodoDAO.findAll();
        List<Aula> aulasOcupadasPeriodicas = new ArrayList<>();
        for (Periodo periodo : periodos) {
            if (!fecha.isBefore(periodo.getFechaInicio()) && !fecha.isAfter(periodo.getFechaFin())) {
                List<Periodica> reservasPeriodicas = reservaDAO
                        .obtenerReservasPorFechasPeriodo(periodo.getFechaInicio(), periodo.getFechaFin());
                aulasOcupadasPeriodicas.addAll(reservasPeriodicas.stream()
                        .flatMap(reserva -> reserva.getDias().stream())
                        .filter(dia -> dia.getDiaSemana().equals(convertirADiaSemana(fecha.getDayOfWeek()))
                                && dia.getHoraInicio().isBefore(horaFin)
                                && dia.getHoraFin().isAfter(horaInicio))
                        .map(Dia::getAula)
                        .distinct()
                        .collect(Collectors.toList()));
            }
        }

        // Combinar aulas ocupadas de reservas esporádicas y periódicas
        Set<Aula> aulasOcupadas = new HashSet<>(aulasOcupadasEsporadicas);
        aulasOcupadas.addAll(aulasOcupadasPeriodicas);

        // Filtrar aulas disponibles
        List<AulaDTO> aulasDisponibles = aulasPorTipo.stream()
                .filter(aulaDTO -> aulaDTO.getCapacidad() >= capacidadMinima)
                .filter(aulaDTO -> aulasOcupadas.stream().noneMatch(aula -> aula.getIdAula() == aulaDTO.getIdAula()))
                .collect(Collectors.toList());

        if (aulasDisponibles.isEmpty()) {
            logger.info("No hay aulas disponibles, buscando aula con menor superposición");
            AulaConHorariosDTO aulaConMenorSuperposicion = obtenerAulaConMenorSuperposicion(tipoClase,
                    reservasEsporadicas, aulasOcupadasPeriodicas, fecha, horaInicio, horaFin, capacidadMinima);
            if (aulaConMenorSuperposicion != null) {
                aulasDisponibles.add(aulaConMenorSuperposicion);
            }
        }

        return aulasDisponibles;
    }

    private AulaConHorariosDTO obtenerAulaConMenorSuperposicion(Class<?> tipoClase,
            List<Esporadica> reservasEsporadicas, List<Periodica> reservasPeriodicas, DiaSemana diaSemana,
            LocalTime horaInicio, LocalTime horaFin, int capacidadMinima) {
        List<AulaDTO> aulasPorTipoDTO = obtenerAulasPorClase(tipoClase);
        List<Aula> aulasPorTipo = aulasPorTipoDTO.stream()
                .map(this::convertirAEntidad)
                .filter(aula -> aula.getCapacidad() >= capacidadMinima)
                .collect(Collectors.toList());

        Map<Integer, Long> superposiciones = new HashMap<>();
        Map<Integer, ReservaSuperpuestaDTO> reservasSuperpuestas = new HashMap<>();

        reservasEsporadicas.stream()
                .flatMap(esporadica -> esporadica.getFechaEspecifica().stream())
                .filter(fechaEspecifica -> convertirADiaSemana(fechaEspecifica.getFecha().getDayOfWeek()).equals(diaSemana)
                        && fechaEspecifica.getHoraInicio().isBefore(horaFin)
                        && fechaEspecifica.getHoraFin().isAfter(horaInicio))
                .forEach(fechaEspecifica -> {
                    long overlap = calcularSuperposicion(horaInicio, horaFin, fechaEspecifica.getHoraInicio(), fechaEspecifica.getHoraFin());
                    superposiciones.put(fechaEspecifica.getAula().getIdAula(), overlap);
                    ReservaSuperpuestaDTO reservaSuperpuestaDTO = convertirAReservaSuperpuestaDTO(fechaEspecifica.getEsporadica());
                    reservasSuperpuestas.putIfAbsent(fechaEspecifica.getAula().getIdAula(), reservaSuperpuestaDTO);
                });

        reservasPeriodicas.stream()
                .flatMap(reserva -> reserva.getDias().stream())
                .filter(dia -> dia.getDiaSemana().equals(diaSemana)
                        && dia.getHoraInicio().isBefore(horaFin)
                        && dia.getHoraFin().isAfter(horaInicio))
                .forEach(dia -> {
                    long overlap = calcularSuperposicion(horaInicio, horaFin, dia.getHoraInicio(), dia.getHoraFin());
                    superposiciones.put(dia.getAula().getIdAula(), overlap);
                    ReservaSuperpuestaDTO reservaSuperpuestaDTO = convertirAReservaSuperpuestaDTO(dia.getPeriodica());
                    reservasSuperpuestas.putIfAbsent(dia.getAula().getIdAula(), reservaSuperpuestaDTO);
                });

        if (superposiciones.isEmpty()) {
            return null;
        }

        Aula aulaConMenorSuperposicion = aulasPorTipo.stream()
                .filter(aula -> superposiciones.containsKey(aula.getIdAula()))
                .min((a1, a2) -> Long.compare(superposiciones.get(a1.getIdAula()), superposiciones.get(a2.getIdAula())))
                .orElse(null);

        if (aulaConMenorSuperposicion != null) {
            HorarioSuperpuestoDTO horario = new HorarioSuperpuestoDTO(horaInicio, horaFin);
            ReservaSuperpuestaDTO reservaSuperpuesta = reservasSuperpuestas.get(aulaConMenorSuperposicion.getIdAula());
            return convertirAEntidadConHorarios(aulaConMenorSuperposicion, horario, reservaSuperpuesta);
        }
        return null;
    }

    private long calcularSuperposicion(LocalTime inicio1, LocalTime fin1, LocalTime inicio2, LocalTime fin2) {
        LocalTime maxInicio = inicio1.isAfter(inicio2) ? inicio1 : inicio2;
        LocalTime minFin = fin1.isBefore(fin2) ? fin1 : fin2;
        return maxInicio.isBefore(minFin) ? java.time.Duration.between(maxInicio, minFin).toMinutes() : 0;
    }

    private ReservaSuperpuestaDTO convertirAReservaSuperpuestaDTO(Reserva reserva) {
        ReservaSuperpuestaDTO dto = new ReservaSuperpuestaDTO();
        dto.setIdReserva(reserva.getIdReserva());
        dto.setNombreProfesor(reserva.getNombreProfesor());
        dto.setApellidoProfesor(reserva.getApellidoProfesor());
        dto.setCorreo(reserva.getCorreo());
        dto.setNombreCatedra(reserva.getNombreCatedra());
        dto.setIdProfesor(reserva.getIdProfesor());
        dto.setIdCatedra(reserva.getIdCatedra());
        return dto;
    }

    private AulaConHorariosDTO obtenerAulaConMenorSuperposicion(Class<?> tipoClase,
            List<Esporadica> reservasEsporadicas, List<Aula> aulasOcupadasPeriodicas, LocalDate fecha,
            LocalTime horaInicio, LocalTime horaFin, int capacidadMinima) {
        List<AulaDTO> aulasPorTipoDTO = obtenerAulasPorClase(tipoClase);
        List<Aula> aulasPorTipo = aulasPorTipoDTO.stream()
                .map(this::convertirAEntidad)
                .filter(aula -> aula.getCapacidad() >= capacidadMinima)
                .collect(Collectors.toList());

        Map<Integer, Long> superposiciones = new HashMap<>();
        Map<Integer, ReservaSuperpuestaDTO> reservasSuperpuestas = new HashMap<>();

        reservasEsporadicas.stream()
                .flatMap(esporadica -> esporadica.getFechaEspecifica().stream())
                .filter(fechaEspecifica -> fechaEspecifica.getFecha().equals(fecha)
                        && fechaEspecifica.getHoraInicio().isBefore(horaFin)
                        && fechaEspecifica.getHoraFin().isAfter(horaInicio))
                .forEach(fechaEspecifica -> {
                    long overlap = calcularSuperposicion(horaInicio, horaFin, fechaEspecifica.getHoraInicio(), fechaEspecifica.getHoraFin());
                    superposiciones.put(fechaEspecifica.getAula().getIdAula(), overlap);
                    ReservaSuperpuestaDTO reservaSuperpuestaDTO = convertirAReservaSuperpuestaDTO(fechaEspecifica.getEsporadica());
                    reservasSuperpuestas.putIfAbsent(fechaEspecifica.getAula().getIdAula(), reservaSuperpuestaDTO);
                });

        List<Periodica> reservasPeriodicas = reservaDAO.obtenerReservasPorFechasPeriodo(fecha.minusDays(1), fecha.plusDays(1));
        reservasPeriodicas.stream()
                .flatMap(reserva -> reserva.getDias().stream())
                .filter(dia -> convertirADiaSemana(fecha.getDayOfWeek()).equals(dia.getDiaSemana())
                        && dia.getHoraInicio().isBefore(horaFin)
                        && dia.getHoraFin().isAfter(horaInicio))
                .forEach(dia -> {
                    long overlap = calcularSuperposicion(horaInicio, horaFin, dia.getHoraInicio(), dia.getHoraFin());
                    superposiciones.put(dia.getAula().getIdAula(), overlap);
                    ReservaSuperpuestaDTO reservaSuperpuestaDTO = convertirAReservaSuperpuestaDTO(dia.getPeriodica());
                    reservasSuperpuestas.putIfAbsent(dia.getAula().getIdAula(), reservaSuperpuestaDTO);
                });

        if (superposiciones.isEmpty()) {
            return null;
        }

        Aula aulaConMenorSuperposicion = aulasPorTipo.stream()
                .filter(aula -> superposiciones.containsKey(aula.getIdAula()))
                .min((a1, a2) -> Long.compare(superposiciones.get(a1.getIdAula()), superposiciones.get(a2.getIdAula())))
                .orElse(null);

        if (aulaConMenorSuperposicion != null) {
            HorarioSuperpuestoDTO horario = new HorarioSuperpuestoDTO(horaInicio, horaFin);
            ReservaSuperpuestaDTO reservaSuperpuesta = reservasSuperpuestas.get(aulaConMenorSuperposicion.getIdAula());
            return convertirAEntidadConHorarios(aulaConMenorSuperposicion, horario, reservaSuperpuesta);
        }
        return null;
    }

    public AulaConHorariosDTO convertirAEntidadConHorarios(Aula aula, HorarioSuperpuestoDTO horarioSuperpuesto, ReservaSuperpuestaDTO reservaSuperpuesta) {
        AulaConHorariosDTO dto = new AulaConHorariosDTO();
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

        if (horarioSuperpuesto != null) {
            dto.setHorarioSuperpuesto(horarioSuperpuesto);
            logger.info("Horario asignado al DTO: " + horarioSuperpuesto.getHoraInicio() + " - "
                    + horarioSuperpuesto.getHoraFin());
        } else {
            logger.info("Horario superpuesto es nulo");
        }

        dto.setReservaSuperpuesta(reservaSuperpuesta);

        logger.info("Convertir aula con horario superpuesto: " + dto);
        return dto;
    }

    @Override
    public AulaDTO convertirADTO(Aula aula) {
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

    @Override
    public Aula convertirAEntidad(AulaDTO dto) {
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
            throw new IllegalArgumentException("Tipo de aula no soportado en DTO");
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

    @Override
    public Aula crearAula(ReservaDTO reservaDTO) {
        Aula aula = null;

        if (reservaDTO.getTipoAula().equals("Multimedio")) {
            aula = new AulaMultimedio();
        } else if (reservaDTO.getTipoAula().equals("Informatica")) {
            aula = new AulaInformatica();
        } else if (reservaDTO.getTipoAula().equals("SinRecursosAdicionales")) {
            aula = new AulaSinRecursosAdicionales();
        } else {
            throw new ValidationException("Hubo un error con el tipo de aula");
        }

        return aula;
    }

    private DiaSemana convertirADiaSemana(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return DiaSemana.LUNES;
            case TUESDAY:
                return DiaSemana.MARTES;
            case WEDNESDAY:
                return DiaSemana.MIERCOLES;
            case THURSDAY:
                return DiaSemana.JUEVES;
            case FRIDAY:
                return DiaSemana.VIERNES;
            default:
                throw new IllegalArgumentException("Día de la semana no soportado: " + dayOfWeek);
        }
    }
}