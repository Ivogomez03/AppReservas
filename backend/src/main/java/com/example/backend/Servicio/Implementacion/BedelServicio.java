package com.example.backend.Servicio.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.DTO.BedelDTO;
import com.example.backend.DTO.ValidarContrasenaDTO;
import com.example.backend.Modelos.Bedel;
import com.example.backend.Repositorio.BedelDAO;
import com.example.backend.Servicio.IBedelServicios;
import com.example.backend.Modelos.TurnoDeTrabajo;
import java.util.stream.Collectors;
import java.util.List;
@Service
public class BedelServicio implements IBedelServicios {
    @Autowired 
    private AdministradorServicio gestorAdmin;
    @Autowired
    private BedelDAO bedelDAO;
    @Autowired
    private ValidarBedelServicio validarBedelServicio;

    @Override
    public ValidarContrasenaDTO validarBedel(BedelDTO bedelDTO) {
        
        ValidarContrasenaDTO salida = new ValidarContrasenaDTO();
        String contra = bedelDTO.getContrasena();

        String errorContrasena = validarBedelServicio.validatePassword(contra);
        String errorId = validarBedelServicio.validarId(bedelDTO.getIdUsuario());

        if (errorContrasena.equals("Contraseña valida") && errorId.equals("Id valida")) {

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

        salida.setErrorContrasena(errorContrasena);
        salida.setErrorId(errorId);

        return salida;
        
    }
    public Bedel buscarBedel(int id){
        return bedelDAO.findById(id).orElse(null);

    }
    public boolean validarBedel(int id, String contrasena) {
        // Busca el bedel por ID
        Bedel bedel = buscarBedel(id);
        
        // Verifica si el bedel existe
        if (bedel == null) {
            throw new IllegalArgumentException("El bedel con ID " + id + " no existe");
        }

        // Compara la contraseña
        if (!bedel.getContrasena().equals(contrasena)) {
            throw new IllegalArgumentException("Contraseña incorrecta para el bedel con ID " + id);
        }

        // Si todo es correcto, devuelve true
        return true;
    }
     public void eliminarBedel(BedelDTO bedelSeleccionado) {
    // Recuperar el ID del objeto BedelDTO recibido
    int id = bedelSeleccionado.getIdUsuario();

    // Buscar el Bedel en la base de datos para confirmar que existe y está habilitado
    Bedel bedel = bedelDAO.findById(id).orElseThrow(() -> 
        new IllegalArgumentException("El bedel con ID " + id + " no existe o ya fue eliminado."));

    // Verificar si el bedel ya está deshabilitado
    if (!bedel.isHabilitado()) {
        throw new IllegalArgumentException("El bedel con ID " + id + " ya está deshabilitado.");
    }

    // Cambiar el atributo habilitado a false
    bedel.setHabilitado(false);

    // Guardar el Bedel actualizado en la base de datos
    bedelDAO.save(bedel);
}

 public List<BedelDTO> buscarBedelesPorApellido(String apellido) {
        List<Bedel> bedeles = bedelDAO.findByApellidoAndHabilitadoTrue(apellido);

        if (bedeles.isEmpty()) {
            throw new IllegalArgumentException("No existen bedeles habilitados con el apellido proporcionado.");
        }

        return bedeles.stream()
                .map(this::convertirABedelDTO)
                .collect(Collectors.toList());
    }

    public List<BedelDTO> buscarBedelesPorTurno(TurnoDeTrabajo turno) {
        List<Bedel> bedeles = bedelDAO.findByTurnoDeTrabajoAndHabilitadoTrue(turno);

        if (bedeles.isEmpty()) {
            throw new IllegalArgumentException("No existen bedeles habilitados con el turno proporcionado.");
        }

        return bedeles.stream()
                .map(this::convertirABedelDTO)
                .collect(Collectors.toList());
    }

    private BedelDTO convertirABedelDTO(Bedel bedel) {
        BedelDTO dto = new BedelDTO();
        dto.setIdUsuario(bedel.getIdUsuario());
        dto.setNombre(bedel.getNombre());
        dto.setApellido(bedel.getApellido());
        dto.setTurnoDeTrabajo(bedel.getTurnoDeTrabajo());
        return dto;
    }
    public BedelDTO modificarBedel(BedelDTO bedelModificado) {
    // Recuperar el ID del BedelDTO recibido
    int id = bedelModificado.getIdUsuario();

    // Buscar el Bedel en la base de datos
    Bedel bedelExistente = bedelDAO.findById(id).orElseThrow(() -> 
        new IllegalArgumentException("El bedel con ID " + id + " no existe."));

//FALTAN LAS VALIDACIONES ANTES DE GUARDAR
    // Actualizar los datos del bedel
    bedelExistente.setApellido(bedelModificado.getApellido());
    bedelExistente.setNombre(bedelModificado.getNombre());
    bedelExistente.setTurnoDeTrabajo(bedelModificado.getTurnoDeTrabajo());
    bedelExistente.setContrasena(bedelModificado.getContrasena());

    // Guardar los cambios en la base de datos
    bedelDAO.save(bedelExistente);

    // Convertir el Bedel actualizado a DTO y devolverlo
    return convertirABedelDTO(bedelExistente);
}
public BedelDTO obtenerDatosBedel(BedelDTO bedelDTO) {
    // Recuperar el ID del BedelDTO
    int id = bedelDTO.getIdUsuario();

    // Buscar el Bedel en la base de datos
    Bedel bedel = bedelDAO.findById(id).orElseThrow(() -> 
        new IllegalArgumentException("El bedel con ID " + id + " no existe."));

    // Convertir el Bedel a DTO
    return convertirABedelDTO(bedel);
}
}
    