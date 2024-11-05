package package com.example.backend.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import TpDiseno.Backend.Modelos.Bedel;
import TpDiseno.Backend.Modelos.TurnoDeTrabajo;
import TpDiseno.Backend.Servicio.Implementacion.BedelServicio;

@RestController
public class BedelControlador {

    @Autowired
    private BedelServicio bedelServicio;

    //Endpoint para obtener todos los bedeles
    @GetMapping("/bedel/traerTodos")
    public List<Bedel> getBedeles() {
        return bedelServicio.getBedeles();
    }

    //Endopoint para obtener un bedel por su id
    @GetMapping("/bedel/traerId/{id}")
    public Bedel getBedelPorId(@PathVariable int id) {
        return bedelServicio.getBedelPorId(id);
    }

    //Endpoint para guardar un bedel
    @PostMapping("/bedel/crear")
    public void crearBedel(@RequestBody Bedel bedel) {
        bedelServicio.guardarBedel(bedel);
    }

    //Endpoint para eliminar un bedel
    @DeleteMapping("/bedel/eliminar/{id}")
    public String eliminarBedel(@PathVariable int id) {

        bedelServicio.eliminarBedel(id);

        return "Bedel eliminado";
    }

    //Endpoint para modificar un bedel
    @PutMapping("/bedel/modificar")
    public Bedel modificarBedel(@PathVariable int idUsuarioVieja,
    @RequestParam(required = false, name = "idUsuario") int idUsuarioNueva,
    @RequestParam(required = false, name = "nombre") String nombre,
    @RequestParam(required = false, name = "apellido") String apellido,
    @RequestParam(required = false, name = "contrasena") String contrasena,
    @RequestParam(required = false, name = "turnoDeTrabajo") TurnoDeTrabajo turnoDeTrabajo,
    @RequestParam(required = false, name = "idAdminCreador") int idAdminCreador) {

        bedelServicio.modificarBedel(idUsuarioVieja, idUsuarioNueva, nombre, apellido, contrasena, turnoDeTrabajo, idAdminCreador);

        Bedel bedel = bedelServicio.getBedelPorId(idUsuarioVieja);

        return bedel;
    }

}
