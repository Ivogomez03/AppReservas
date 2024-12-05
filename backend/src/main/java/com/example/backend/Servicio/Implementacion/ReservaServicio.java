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
import com.example.backend.Modelos.Periodo;
import com.example.backend.Repositorio.PeriodoDAO;
import com.example.backend.Repositorio.ReservaDAO;
import com.example.backend.Servicio.IReservaServicio;

@Service
public class ReservaServicio implements IReservaServicio {

    @Autowired
    private ReservaDAO reservaDAO;

    @Autowired
    private AulaServicio aulaServicio;

    @Autowired
    private PeriodoDAO periodoDAO;

    @Autowired
    private EsporadicaServicio esporadicaServicio;


    //Registrar una reserva
    @Override
    public List<CDU01ReservasYAulas> registrarReserva(ReservaDTO reserva) throws ValidationException {
        
        if(this.validarDatos(reserva) && this.validarHorasInicioDuracion(reserva) && this.validarDuracionMultiplo30(reserva) && this.validarFechaActual(reserva)){
            return this.obtenerAulas(reserva);
        }

       throw new ValidationException("Hubo un error al registrar la reserva");

    }

    // Validar que la reserva no se superponga con otra
    @Override
    public boolean validarHorasInicioDuracion(ReservaDTO reserva) {

        List<CDU01DiasDTO> dias = reserva.getDias();
        Set<DiaSemana> diasUnicos = new HashSet<>();

        for (CDU01DiasDTO dia : dias) {
            if (!diasUnicos.add(dia.getDia())) {
                // Si no se puede agregar al conjunto, significa que el día está duplicado
                throw new ValidationException("No puede haber más de una hora de inicio y duración para un día");
            }
        }
        return true;
    }

    // Validar que la duración sea un múltiplo de 30 minutos
    @Override
    public boolean validarDuracionMultiplo30(ReservaDTO reserva) {

        List<CDU01DiasDTO> dias = reserva.getDias();

        for (CDU01DiasDTO dia : dias) {
            if (dia.getDuracion() % 30 != 0) {
                throw new ValidationException("La duración debe ser un múltiplo de 30 minutos");
            }
        }
        return true;
    }

    // Validar que la fecha ingresada sea posterior a la actual
    @Override
    public boolean validarFechaActual(ReservaDTO reserva) {

        List<CDU01FechaDTO> fechas = reserva.getFechasespecificas();
        for (CDU01FechaDTO fecha : fechas) {
            if (fecha.getFecha().isBefore(LocalDate.now()) || fecha.getFecha().isEqual(LocalDate.now())) {
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
    public boolean validarNombre(String nombre){

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


    public List<Periodica> obtenerReservasPorPeriodo(int idPeriodo) {
        return reservaDAO.obtenerReservasPorPeriodo(idPeriodo);
    }

    public List<Esporadica> obtenerReservasPorFecha(LocalDate fecha) {
        return reservaDAO.obtenerReservasPorFecha(fecha);
    }
    

    public List<CDU01ReservasYAulas> obtenerAulas(ReservaDTO reserva) {

        List<CDU01ReservasYAulas> reservaYAulas = new ArrayList<>();

        if(reserva.isEsporadica()){
            for(CDU01FechaDTO fecha : reserva.getFechasespecificas()){
                CDU01ReservasYAulas reservaYAula = new CDU01ReservasYAulas();
                reservaYAula.setAulas(aulaServicio.obtenerAulasDisponiblesEsporadicas(null, fecha.getFecha(), fecha.getHoraInicio(), fecha.getHoraInicio().plusMinutes(fecha.getDuracion())));
                reservaYAula.setFechas(fecha);
                reservaYAulas.add(reservaYAula);  
            }
        }
        else if(!reserva.isEsporadica()){

            int idPeriodoAnual =-1, idPeriodoPrimerCuatrimestre = -1, idPeriodoSegundoCuatrimestre = -1;
            Iterable <Periodo> periodos = periodoDAO.findAll();

            for (Periodo elemento : periodos) {
                if (elemento.getFechaInicio().getYear() == LocalDate.now().getYear()) {
                    switch (elemento.getTipoPeriodo()) {
                        case ANUAL:
                            idPeriodoAnual = elemento.getIdPeriodo();
                            break;
                        case PRIMERCUATRIMESTRE:
                            idPeriodoPrimerCuatrimestre = elemento.getIdPeriodo();
                            break;
                        case SEGUNDOCUATRIMESTRE:
                            idPeriodoSegundoCuatrimestre = elemento.getIdPeriodo();
                            break;
                        default:
                            throw new ValidationException("Hubo un error con el tipo de periodo");
                    }
                }
            }

            if (reserva.isPeriodicaAnual() && idPeriodoAnual == -1) {
                throw new ValidationException("No se encontró un período anual válido para el año actual.");
            }
            if (reserva.isPeriodicaPrimerCuatrimestre() && idPeriodoPrimerCuatrimestre == -1) {
                throw new ValidationException("No se encontró un período del primer cuatrimestre válido para el año actual.");
            }
            if (reserva.isPeriodicaSegundoCuatrimestre() && idPeriodoSegundoCuatrimestre == -1) {
                throw new ValidationException("No se encontró un período del segundo cuatrimestre válido para el año actual.");
            }
            
            if(reserva.isPeriodicaAnual()){
                for(CDU01DiasDTO dia : reserva.getDias()){
                    CDU01ReservasYAulas reservaYAula = new CDU01ReservasYAulas();
                    reservaYAula.setAulas(aulaServicio.obtenerAulasDisponiblesPeriodicasConPeriodo(null, idPeriodoAnual, dia.getDia(), dia.getHoraInicio(), dia.getHoraInicio().plusMinutes(dia.getDuracion())));
                    reservaYAula.setDias(dia);
                    reservaYAulas.add(reservaYAula);  
                }
            }
            else if(reserva.isPeriodicaPrimerCuatrimestre()){
                for(CDU01DiasDTO dia : reserva.getDias()){
                    CDU01ReservasYAulas reservaYAula = new CDU01ReservasYAulas();
                    reservaYAula.setAulas(aulaServicio.obtenerAulasDisponiblesPeriodicasConPeriodo(null, idPeriodoPrimerCuatrimestre, dia.getDia(), dia.getHoraInicio(),  dia.getHoraInicio().plusMinutes(dia.getDuracion())));
                    reservaYAula.setDias(dia);
                    reservaYAulas.add(reservaYAula);  
                }
            }
            else if(reserva.isPeriodicaSegundoCuatrimestre()){
                for(CDU01DiasDTO dia : reserva.getDias()){
                    CDU01ReservasYAulas reservaYAula = new CDU01ReservasYAulas();
                    reservaYAula.setAulas(aulaServicio.obtenerAulasDisponiblesPeriodicasConPeriodo(null, idPeriodoSegundoCuatrimestre, dia.getDia(), dia.getHoraInicio(),  dia.getHoraInicio().plusMinutes(dia.getDuracion())));
                    reservaYAula.setDias(dia);
                    reservaYAulas.add(reservaYAula);  
                }
            }
        }
        else{
            throw new ValidationException("Hubo un error con el tipo de reserva");
        }
        return reservaYAulas;
    }

    @Override
    public void guardarReserva(List<CDU01ReservaYAulaFinal> reservaYAula, ReservaDTO reserva) {
        if(reserva.isEsporadica()){
            esporadicaServicio.guardarReservaEsporadica(reservaYAula, reserva);
        }
        else {
            
        }
    }
}
 