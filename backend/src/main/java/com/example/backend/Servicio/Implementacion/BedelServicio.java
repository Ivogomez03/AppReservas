package com.example.backend.Servicio.Implementacion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.DTO.BedelDTO;
import com.example.backend.DTO.BusquedaDTO;
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
            bedel.setActivo(bedelDTO.isActivo());
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

    @Override
    public String modificarBedel(BedelDTO bedeldto) {
       
        Bedel bedel = bedelDAO.findById(bedeldto.getIdUsuario()).get();

        
       if(!validarBedelServicio.validatePassword(bedel.getContrasena()).equals("contraseña valida")){
            return "Contraseña invalida";
       }
       else{
        
        bedel.setNombre(bedeldto.getNombre());
        bedel.setApellido(bedeldto.getApellido());
        bedel.setContrasena(bedeldto.getContrasena());
        bedel.setTurnoDeTrabajo(bedeldto.getTurnoDeTrabajo());
        bedel.setActivo(bedeldto.isActivo());
        bedel.setAdminCreador(gestorAdmin.buscarAdministrador(bedeldto.getIdAdminCreador()));

        bedelDAO.save(bedel);

        return "Guardado con exito";
       }

    }

    @Override
    public BedelDTO obtenerBedel(int id) {
        Bedel bedel = bedelDAO.findById(id).get();

        BedelDTO bedeldto = new BedelDTO();
        bedeldto.setNombre(bedel.getNombre());
        bedeldto.setApellido(bedel.getApellido());
        bedeldto.setIdUsuario(bedel.getIdUsuario());
        bedeldto.setContrasena(bedel.getContrasena());
        bedeldto.setTurnoDeTrabajo(bedel.getTurnoDeTrabajo());
        bedeldto.setActivo(bedel.isActivo());
        bedeldto.setIdAdminCreador(bedel.getAdminCreador().getIdUsuario());

        return bedeldto;
    }
    @Override
    public String eliminarBedel(int id) {
        
        Bedel bedel = bedelDAO.findById(id).get();
        bedel.setActivo(false);
        bedelDAO.save(bedel);
        return "Bedel eliminado";
    }
    @Override
    public List<BedelDTO> obtenerBedel(BusquedaDTO busqueda) {
        List<Bedel> bedelLista = bedelDAO.findBedelByNombre(busqueda.getNombre());

        return bedelLista.stream()
                .map(obj -> new BedelDTO(obj.getIdUsuario(), obj.getNombre(), obj.getApellido(), obj.getContrasena(), obj.getTurnoDeTrabajo(), obj.getAdminCreador().getIdUsuario(), obj.getAdminCreador(), obj.isActivo()))
                .collect(Collectors.toList());
    }
    
    
}