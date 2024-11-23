package com.example.backend.Servicio.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.DTO.BedelDTO;
import com.example.backend.DTO.ValidarContrasenaDTO;
import com.example.backend.Modelos.Bedel;
import com.example.backend.Repositorio.BedelDAO;
import com.example.backend.Servicio.IBedelServicios;
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
    
}