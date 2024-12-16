package com.example.backend.Servicio.Implementacion;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.DTO.BedelDTO;
import com.example.backend.DTO.ValidarContrasenaDTO;
import com.example.backend.Excepciones.ValidationException;
import com.example.backend.Modelos.Administrador;
import com.example.backend.Modelos.Bedel;
import com.example.backend.Modelos.TurnoDeTrabajo;
import com.example.backend.Repositorio.AdministradorDAO;
import com.example.backend.Repositorio.BedelDAO;
import com.example.backend.Servicio.IBedelServicios;

@Service
public class BedelServicio implements IBedelServicios {
    @Autowired
    private AdministradorServicio gestorAdmin;
    @Autowired
    private BedelDAO bedelDAO;
    @Autowired
    private AdministradorDAO adminDAO;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*]).{8,16}$");

    @Override
    public String validatePassword(String contra) {
        if (!PASSWORD_PATTERN.matcher(contra).matches()) {
            return "La contraseña debe tener al menos de 8 caracteres, un maximo de 16 caracteres, al menos un número, una mayúscula y un caracter especial.";
        }
        return "Contraseña valida";
    }

    @Override
    public String existeBedelPorId(int id) {

        if (bedelDAO.findById(id).isPresent()) {
            return "Id ya existente";
        } else {
            return "Id valida";
        }

    }

    @Override
    public ValidarContrasenaDTO validarBedel(BedelDTO bedelDTO) {

        ValidarContrasenaDTO salida = new ValidarContrasenaDTO();
        String contra = bedelDTO.getContrasena();

        String errorContrasena = this.validatePassword(contra);
        String errorId = this.existeBedelPorId(bedelDTO.getIdUsuario());
        Administrador idAdmin = adminDAO.findById(bedelDTO.getIdUsuario()).get();

        if (errorContrasena.equals("Contraseña valida") && errorId.equals("Id valida") && idAdmin == null) {

            Bedel bedel = new Bedel();
            bedel.setNombre(bedelDTO.getNombre());
            bedel.setApellido(bedelDTO.getApellido());
            bedel.setIdUsuario(bedelDTO.getIdUsuario());
            bedel.setContrasena(bedelDTO.getContrasena());
            bedel.setTurnoDeTrabajo(bedelDTO.getTurnoDeTrabajo());
            bedel.setHabilitado(true);
            bedel.setAdminCreador(gestorAdmin.buscarAdministrador(bedelDTO.getIdAdminCreador()));

            bedelDAO.save(bedel);
        }
        else if(!errorContrasena.equals("Contraseña valida")){
            throw new ValidationException("La contraseña no es valida");
        }
        else if(!errorId.equals("Id valida")){
            throw new ValidationException("El id ya existe");
        }
        else if(idAdmin != null){
            throw new ValidationException("Hay un administrador con el mismo id");
        }

        salida.setErrorContrasena(errorContrasena);
        salida.setErrorId(errorId);

        return salida;

    }

    @Override
    public Bedel buscarBedel(int id) {
        return bedelDAO.findById(id).orElse(null);

    }

    @Override
    public boolean validarBedel(int id, String contrasena) {
        // Busca el bedel por ID
        Bedel bedel = buscarBedel(id);

        // Verifica si el bedel existe
        if (bedel == null) {
            return false;
        }

        // Compara la contraseña
        if (!bedel.getContrasena().equals(contrasena)) {
            return false;
        }

        // Si todo es correcto, devuelve true
        return true;
    }

    @Override
    public void eliminarBedel(BedelDTO bedelSeleccionado) {
        // Buscar el Bedel en la base de datos;
        Bedel bedel = bedelDAO.findById(bedelSeleccionado.getIdUsuario()).get();
        if(bedel == null){
            throw new ValidationException("El bedel no esta en la base de datos") ;
        }

        // Cambiar el atributo habilitado a false
        bedel.setHabilitado(false);

        // Guardar el Bedel actualizado en la base de datos
        bedelDAO.save(bedel);   
    }

    @Override
    public List<BedelDTO> buscarBedelesPorTurnoyApellido(TurnoDeTrabajo turno, String apellido) {

        List<Bedel> bedeles;

        // Si no hay apellido y hay turno
        if ((apellido == null || apellido.isEmpty()) && turno != null) {
            if (turno == TurnoDeTrabajo.Todos) {
                bedeles = bedelDAO.findByHabilitadoTrue();
            } else {
                bedeles = bedelDAO.findByTurnoDeTrabajoAndHabilitadoTrue(turno);
            }
        }
        // Si hay apellido y no hay turno
        else if (turno == TurnoDeTrabajo.Todos && (apellido != null && !apellido.isEmpty())) {
            bedeles = bedelDAO.findByApellidoAndHabilitadoTrue(apellido);
        }
        // Si hay ambos criterios
        else if (turno != null && (apellido != null && !apellido.isEmpty())) {
            bedeles = bedelDAO.buscarPorApellidoYTurno(apellido, turno);
        }
        // Si no hay ningún criterio, lanza una excepción o maneja el caso
        else {
            throw new IllegalArgumentException("Debe proporcionar al menos un criterio de búsqueda.");
        }

        // Si no se encuentran bedeles, lanza una excepción
        if (bedeles.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron bedeles con los criterios proporcionados.");
        }

        // Convierte la lista de Bedel a BedelDTO
        return bedeles.stream()
                .map(this::convertirABedelDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BedelDTO convertirABedelDTO(Bedel bedel) {
        BedelDTO dto = new BedelDTO();
        dto.setIdUsuario(bedel.getIdUsuario());
        dto.setNombre(bedel.getNombre());
        dto.setApellido(bedel.getApellido());
        dto.setTurnoDeTrabajo(bedel.getTurnoDeTrabajo());
        return dto;
    }

    @Override
    public String modificarBedel(BedelDTO bedelDTO) {

        String contraValida = this.validatePassword(bedelDTO.getContrasena());

        if (contraValida.equals("Contraseña valida")) {
            // datos nulos o caracteres maximos se valida en front
            Bedel bedel = bedelDAO.findById(bedelDTO.getIdUsuario()).get();
            if (bedel == null) {
                throw new ValidationException("El bedel no esta en la base de datos") ;
            }
            else{
                bedel.setNombre(bedelDTO.getNombre());
                bedel.setApellido(bedelDTO.getApellido());
                bedel.setIdUsuario(bedelDTO.getIdUsuario());
                bedel.setContrasena(bedelDTO.getContrasena());
                bedel.setTurnoDeTrabajo(bedelDTO.getTurnoDeTrabajo());
                bedel.setHabilitado(true);
                bedel.setAdminCreador(gestorAdmin.buscarAdministrador(bedelDTO.getIdAdminCreador()));
    
                // Guardar los cambios en la base de datos
                bedelDAO.save(bedel);
    
                // Convertir el Bedel actualizado a DTO y devolverlo
                return "Bedel ha sido modificado correctamente";
            }
        } else {
            throw new ValidationException(contraValida);
        }

    }

    @Override
    public BedelDTO obtenerDatosBedel(BedelDTO bedelDTO) {
        // Recuperar el ID del BedelDTO
        int id = bedelDTO.getIdUsuario();

        // Buscar el Bedel en la base de datos
        Bedel bedel = bedelDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El bedel con ID " + id + " no existe."));

        // Convertir el Bedel a DTO
        return convertirABedelDTO(bedel);
    }

    
}
