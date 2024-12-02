package com.example.backend.Servicio.Implementacion;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.stream.Collectors;
import com.example.backend.DTO.AulaDTO;
import com.example.backend.DTO.PeriodosDTO;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.DTO.ReservaSingularDTO;
import com.example.backend.Excepciones.ValidationException;
import com.example.backend.Modelos.DiaSemana;
import com.example.backend.Modelos.Esporadica;
import com.example.backend.Servicio.IReservaServicio;

public class ReservaServicio implements IReservaServicio {

    @Autowired
    private FechaEspecificaServicio fechaEspecificaServicio;

    //Registrar una reserva
    @Override
    public List<AulaDTO> registrarReserva(ReservaDTO reserva) throws ValidationException {
        
        if(this.validarDatos(reserva) && this.validarHorasInicioDuracion(reserva) && this.validarDuracionMultiplo30(reserva) && this.validarFechaActual(reserva)){
            List<AulaDTO> aulas = null;

            return aulas;
        }

        else{
            return null;
        }

    }

    // Validar que la reserva no se superponga con otra
    @Override
    public boolean validarHorasInicioDuracion(ReservaDTO reserva) {

        List<PeriodosDTO> periodos = reserva.getPeriodos();
        Set<DiaSemana> diasUnicos = new HashSet<>();

        for (PeriodosDTO periodo : periodos) {
            if (!diasUnicos.add(periodo.getDia())) {
                // Si no se puede agregar al conjunto, significa que el día está duplicado
                throw new ValidationException("No puede haber más de una hora de inicio y duración para un día");
            }
        }
        return true;
    }

    // Validar que la duración sea un múltiplo de 30 minutos
    @Override
    public boolean validarDuracionMultiplo30(ReservaDTO reserva) {

        List<PeriodosDTO> periodos = reserva.getPeriodos();

        for (PeriodosDTO periodo : periodos) {
            if (periodo.getDuracion() % 30 != 0) {
                throw new ValidationException("La duración debe ser un múltiplo de 30 minutos");
            }
        }
        return true;
    }

    // Validar que la fecha ingresada sea posterior a la actual
    @Override
    public boolean validarFechaActual(ReservaDTO reserva) {

        List<LocalDate> fechas = reserva.getFechas();
        for (LocalDate fecha : fechas) {
            if (fecha.isBefore(LocalDate.now()) || fecha.isEqual(LocalDate.now())) {
                throw new ValidationException("La fecha no puede ser anterior o igual a la fecha actual");
            }
        }
        return true;
    }

    //Validar datos
    @Override
    public boolean validarDatos(ReservaDTO reserva) {
        
        if(this.validarNombre(reserva.getNombreProfesor()) && this.valdiarApellido(reserva.getApellidoProfesor()) && this.validarCorreo(reserva.getCorreo()) && this.validarNombreCatedra(reserva.getNombreCatedra())){
            return true;
        }
        else{
            return false;
        }
    }    
    
    //Validar nombre
    private static final String NOMBRE_PROFESOR_REGEX = "^[A-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"; // Expresión regular para validar el nombre del profesor (solo letras y espacios)
    @Override
    public boolean validarNombre(String nombre) {

        Pattern pattern = Pattern.compile(NOMBRE_PROFESOR_REGEX);

        if(nombre == null) {
            throw new ValidationException("El nombre del profesor no puede ser nulo");
        }
        else if (!pattern.matcher(nombre).matches()){
            throw new ValidationException("El nombre del profesor no es válido");
        }
        return true;
    }

    //Validar apellido
    @Override
    public boolean valdiarApellido(String nombre) {

        Pattern pattern = Pattern.compile(NOMBRE_PROFESOR_REGEX);

        if(nombre == null) {
            throw new ValidationException("El apellido del profesor no puede ser nulo");
        }
        else if (!pattern.matcher(nombre).matches()){
            throw new ValidationException("El apellido del profesor no es válido");
        }
        return true;
    }

    //Validar correo
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"; // Expresión regular para validar el correo electrónico
    @Override
    public boolean validarCorreo(String correo) {

        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        if(correo == null) {
            throw new ValidationException("El correo no puede ser nulo");
        }
        else if (!pattern.matcher(correo).matches()){
            throw new ValidationException("El correo no es válido");
        }
        return true;
    }

    //Validar nombre de la catedra
    @Override
    public boolean validarNombreCatedra(String catedra) {

        Pattern pattern = Pattern.compile(NOMBRE_PROFESOR_REGEX);

        if(catedra == null) {
            throw new ValidationException("El nombre de la catedra no puede ser nulo");
        }
        else if (!pattern.matcher(catedra).matches()){
            throw new ValidationException("El nombre de la catedra no es válido");
        }
        return true;
    }

    public void guardarReserva(ReservaSingularDTO reserva, AulaDTO aulaDTO) {
        if(reserva.isEsporadica()){
            this.guardarReservaEsporadica(reserva, aulaDTO);
        }
        else if(reserva.isPeriodicaAnual()){

        }
        else if(reserva.isPeriodicaPrimerCuatrimestre()){

        }
        else if(reserva.isPeriodicaSegundoCuatrimestre()){

        }
        else{
            throw new ValidationException("Hubo un error con el tipo de reserva");
        }
    
    }
     public List<Aula> obtenerAulasDisponiblesPeriodicasConPeriodo(DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin, Periodo periodo) {
    // Recuperar aulas ocupadas con el periodo actual
    List<Aula> aulasOcupadas = AulaDAO.findAulasOcupadasPeriodicasConPeriodo(diaSemana.name(), horaInicio, horaFin, periodo.getIdPeriodo());

    // Recuperar todas las aulas
    List<Aula> todasLasAulas = AulaDAO.findAll();
    // Filtrar disponibles
    List<Aula> aulasDisponibles = todasLasAulas.stream()
            .filter(aula -> !aulasOcupadas.contains(aula))
            .collect(Collectors.toList());

    // Verificar si la lista de aulas disponibles está vacía
    if (aulasDisponibles.isEmpty()) {
        System.out.println("No hay aulas disponibles para la fecha especificada.");
        List<Dia> diasOcupados = DiaDAO.findByPeriodo_IdPeriodoAndDiaSemana(periodo.getIdPeriodo(), diaSemana.name());
        return calcularAulasMenorSuperposicion(diasOcupados, horaInicio, horaFin);
    } else {
        return aulasDisponibles;
    }
}
    public List<Aula> obtenerAulasDisponiblesEsporadicas(LocalDate fecha) {
    // Recuperar aulas ocupadas en la fecha específica
    List<Aula> aulasOcupadas = AulaDAO.findAulasOcupadasEsporadicas(fecha);

    // Recuperar todas las aulas
    List<Aula> todasLasAulas = AulaDAO.findAll();
    List<Aula> aulasDisponibles = todasLasAulas.stream()
            .filter(aula -> !aulasOcupadas.contains(aula))
            .collect(Collectors.toList());

    // Verificar si la lista de aulas disponibles está vacía
    if (aulasDisponibles.isEmpty()) {
        System.out.println("No hay aulas disponibles para la fecha especificada.");
        List<Dia> diasOcupados = DiaDAO.findByFecha(fecha);
        return calcularAulasMenorSuperposicion(diasOcupados, horaInicio, horaFin);
    } else {
        return aulasDisponibles;
    }
}


public List<Aula> calcularAulasMenorSuperposicion(List<Dia> diasOcupados, LocalTime horaInicio, LocalTime horaFin) {
    Map<Aula, Long> aulasConSuperposicion = new HashMap<>();

    for (Dia dia : diasOcupados) {
        // Calcular el tiempo de superposición
        long tiempoInicio = Math.max(dia.getHoraInicio().toNanoOfDay(), horaInicio.toNanoOfDay());
        long tiempoFin = Math.min(dia.getHoraFin().toNanoOfDay(), horaFin.toNanoOfDay());

        if (tiempoInicio < tiempoFin) { // Existe superposición
            long tiempoSuperpuesto = tiempoFin - tiempoInicio;
            aulasConSuperposicion.put(dia.getAula(), tiempoSuperpuesto);
        }
    }

    // Ordenar el mapa por el tiempo de superposición y obtener las tres primeras aulas
    List<Aula> tresAulasConMenorSuperposicion = aulasConSuperposicion.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .limit(3)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

    return tresAulasConMenorSuperposicion;
}
    //Guardar reserva Esporadica
    @Override
    public void guardarReservaEsporadica(ReservaSingularDTO reserva, AulaDTO aulaDTO) {

        Esporadica esporadica = new Esporadica();

        esporadica.setIdReserva(reserva.getIdReserva());
        esporadica.setNombreCatedra(reserva.getNombreProfesor());
        esporadica.setApellidoProfesor(reserva.getApellidoProfesor());
        esporadica.setCorreo(reserva.getCorreo());
        esporadica.setNombreCatedra(reserva.getNombreCatedra());
        esporadica.setIdProfesor(reserva.getIdProfesor());
        esporadica.setIdCatedra(reserva.getIdCatedra());
        esporadica.setFechaEspecifica(fechaEspecificaServicio.crearFechaEspecifica(reserva, aulaDTO));

}
