package com.example.backend.Servicio;
import java.util.List;

import com.example.backend.Modelos.Bedel;
import com.example.backend.Modelos.TurnoDeTrabajo;

public interface IBedelServicios {

    //Obetener todos los bedeles
    public List<Bedel> getBedeles();

    //Obtener un bedel por su id
    public Bedel getBedelPorId(int id);

    //Guardar un bedel
    public void guardarBedel(Bedel bedel);

    //Eliminar un bedel
    public void eliminarBedel(int id);

    //modificar un bedel
    public void modificarBedel(int idUsuarioVieja, int idUsuarioNueva, String nombre, String apellido, String contrasena, TurnoDeTrabajo turnoDeTrabajo, int idAdminCreador);
}
