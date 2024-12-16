package com.example.backend.Servicio.Implementacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.DTO.CDU01FechaDTO;
import com.example.backend.DTO.CDU01DiasDTO;
import com.example.backend.DTO.CDU01ReservaYAulaFinal;
import com.example.backend.DTO.CDU01ReservasYAulas;
import com.example.backend.DTO.ReservaDTO;
import com.example.backend.Excepciones.ValidationException;
import com.example.backend.Modelos.DiaSemana;
import com.example.backend.Modelos.Esporadica;
import com.example.backend.Modelos.Periodica;
import com.example.backend.Repositorio.ReservaDAO;
import com.example.backend.Servicio.IReservaServicio;

@Service
public class ReservaServicio implements IReservaServicio {

    @Autowired
    private ReservaDAO reservaDAO;

    @Autowired
    private AulaServicio aulaServicio;

    @Autowired
    private EsporadicaServicio esporadicaServicio;

    @Autowired
    private PeriodicaServicio periodicasServicio;

    @Autowired
    private PeriodoServicio periodoServicio;

    // Registrar una reserva
    @Override
    public List<CDU01ReservasYAulas> registrarReserva(ReservaDTO reserva)
            throws ValidationException, ClassNotFoundException {

        if (this.validarDatos(reserva) && this.validarHorasInicioDuracion(reserva)) {
            return this.obtenerAulas(reserva);
        }

        throw new ValidationException("Hubo un error al registrar la reserva");

    }

    // Validar que la reserva no se superponga con otra
    @Override
    public boolean validarHorasInicioDuracion(ReservaDTO reserva) {

        List<CDU01FechaDTO> fechas = reserva.getFechasespecificas();
        List<CDU01DiasDTO> dias = reserva.getDias();
        Set<DiaSemana> diasUnicos = new HashSet<>();
        Set<LocalDate> fechasUnicas = new HashSet<>();
        if (reserva.isEsporadica()) {
            for (CDU01FechaDTO fecha : fechas) {
                if (!fechasUnicas.add(fecha.getFecha())) {
                    // Si no se puede agregar al conjunto, significa que el día está duplicado
                    throw new ValidationException("No puede haber más de una hora de inicio y duración para una fecha");
                }
            }
            return true;
        } else {
            for (CDU01DiasDTO dia : dias) {
                if (!diasUnicos.add(dia.getDia())) {
                    // Si no se puede agregar al conjunto, significa que el día está duplicado
                    throw new ValidationException("No puede haber más de una hora de inicio y duración para un día");
                }
            }
            return true;
        }

    }

    // Validar datos
    @Override
    public boolean validarDatos(ReservaDTO reserva) {

        if (this.validarNombre(reserva.getNombreProfesor()) && this.valdiarApellido(reserva.getApellidoProfesor())
                && this.validarCorreo(reserva.getCorreo()) && this.validarNombreCatedra(reserva.getNombreCatedra())) {
            return true;
        } else {
            return false;
        }
    }

    // Validar nombre
    private static final String NOMBRE_PROFESOR_REGEX = "^[A-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"; // Expresión regular
                                                                                                   // para validar el
                                                                                                   // nombre del
                                                                                                   // profesor (solo
                                                                                                   // letras y espacios)

    @Override
    public boolean validarNombre(String nombre) {

        Pattern pattern = Pattern.compile(NOMBRE_PROFESOR_REGEX);

        if (nombre == null) {
            throw new ValidationException("El nombre del profesor no puede ser nulo");
        } else if (!pattern.matcher(nombre).matches()) {
            throw new ValidationException("El nombre del profesor no es válido");
        }
        return true;
    }

    private static final String APELLIDO_PROFESOR_REGEX = "^[A-Z][a-zA-Z]*(\\s[A-Z][a-zA-Z]*)?$";

    // Validar apellido
    @Override
    public boolean valdiarApellido(String nombre) {

        Pattern pattern = Pattern.compile(APELLIDO_PROFESOR_REGEX);

        if (nombre == null) {
            throw new ValidationException("El apellido del profesor no puede ser nulo");
        } else if (!pattern.matcher(nombre).matches()) {
            throw new ValidationException("El apellido del profesor no es válido");
        }
        return true;
    }

    // Validar correo
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"; // Expresión regular
                                                                                                   // para validar el
                                                                                                   // correo electrónico

    @Override
    public boolean validarCorreo(String correo) {

        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        if (correo == null) {
            throw new ValidationException("El correo no puede ser nulo");
        } else if (!pattern.matcher(correo).matches()) {
            throw new ValidationException("El correo no es válido");
        }
        return true;
    }

    // Validar nombre de la catedra
    @Override
    public boolean validarNombreCatedra(String catedra) {

        Pattern pattern = Pattern.compile(NOMBRE_PROFESOR_REGEX);

        if (catedra == null) {
            throw new ValidationException("El nombre de la catedra no puede ser nulo");
        } else if (!pattern.matcher(catedra).matches()) {
            throw new ValidationException("El nombre de la catedra no es válido");
        }
        return true;
    }

    @Override
    public List<Periodica> obtenerReservasPorPeriodo(int idPeriodo) {
        return reservaDAO.obtenerReservasPorPeriodo(idPeriodo);
    }

    @Override
    public List<Esporadica> obtenerReservasPorFecha(LocalDate fecha) {
        return reservaDAO.obtenerReservasPorFecha(fecha);
    }

    @Override
    public void guardarReserva(List<CDU01ReservaYAulaFinal> reservaYAula, ReservaDTO reserva) {
        if (reserva.isEsporadica()) {
            esporadicaServicio.guardarReservaEsporadica(reservaYAula, reserva);
        } else {
            periodicasServicio.guardarReservaPeriodica(reserva, reservaYAula);
        }
    }

    @Override
    public List<CDU01ReservasYAulas> obtenerAulas(ReservaDTO reserva) throws ClassNotFoundException {

        Class<?> clase = aulaServicio.crearAula(reserva).getClass();

        List<CDU01ReservasYAulas> reservaYAulas = new ArrayList<>();

        if (reserva.isEsporadica()) {

            for (CDU01FechaDTO fecha : reserva.getFechasespecificas()) {
                CDU01ReservasYAulas reservaYAula = new CDU01ReservasYAulas();
                reservaYAula.setAulas(
                        aulaServicio.obtenerAulasDisponiblesEsporadicas(clase, fecha.getFecha(), fecha.getHoraInicio(),
                                fecha.getHoraInicio().plusMinutes(fecha.getDuracion()), reserva.getCantidadAlumnos()));
                reservaYAula.setFechas(fecha);
                reservaYAulas.add(reservaYAula);
            }

        }

        else if (!reserva.isEsporadica()) {

            int idPeriodoReserva = periodoServicio.obtenerPeriodo(reserva).getIdPeriodo();
            System.out.println("El periodo es " + idPeriodoReserva);
            if (reserva.isPeriodicaAnual()) {
                for (CDU01DiasDTO dia : reserva.getDias()) {
                    CDU01ReservasYAulas reservaYAula = new CDU01ReservasYAulas();
                    reservaYAula.setAulas(aulaServicio.obtenerAulasDisponiblesPeriodicas(clase, idPeriodoReserva,
                            dia.getDia(), dia.getHoraInicio(), dia.getHoraInicio().plusMinutes(dia.getDuracion()),
                            reserva.getCantidadAlumnos()));
                    reservaYAula.setDias(dia);
                    reservaYAulas.add(reservaYAula);
                }
            } else if (reserva.isPeriodicaPrimerCuatrimestre()) {
                for (CDU01DiasDTO dia : reserva.getDias()) {
                    CDU01ReservasYAulas reservaYAula = new CDU01ReservasYAulas();
                    reservaYAula.setAulas(aulaServicio.obtenerAulasDisponiblesPeriodicas(clase, idPeriodoReserva,
                            dia.getDia(), dia.getHoraInicio(), dia.getHoraInicio().plusMinutes(dia.getDuracion()),
                            reserva.getCantidadAlumnos()));
                    reservaYAula.setDias(dia);
                    reservaYAulas.add(reservaYAula);
                }
            } else if (reserva.isPeriodicaSegundoCuatrimestre()) {
                for (CDU01DiasDTO dia : reserva.getDias()) {
                    CDU01ReservasYAulas reservaYAula = new CDU01ReservasYAulas();
                    reservaYAula.setAulas(aulaServicio.obtenerAulasDisponiblesPeriodicas(clase, idPeriodoReserva,
                            dia.getDia(), dia.getHoraInicio(), dia.getHoraInicio().plusMinutes(dia.getDuracion()),
                            reserva.getCantidadAlumnos()));
                    reservaYAula.setDias(dia);
                    reservaYAulas.add(reservaYAula);
                }
            }
        } else {
            throw new ValidationException("Hubo un error con el tipo de reserva");
        }
        return reservaYAulas;

    }
}
