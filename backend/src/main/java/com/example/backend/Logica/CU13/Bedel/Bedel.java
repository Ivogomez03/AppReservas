package com.example.backend.Logica.CU13.Bedel;
import com.example.backend.Logica.usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity

@Table(name = "bedel")
public class Bedel extends usuario {

    @Enumerated(EnumType.STRING)
    @Column(name = "turnoDeTrabajo")
    private TurnoDeTrabajo turnoDeTrabajo;

    @Column(name = "idAdminCreador")
    private int idAdminCreador;

    public Bedel() {
        super();
    }

    public Bedel(String nombre, String apellido, int idUsuario, String contrasena, TurnoDeTrabajo turnoDeTrabajo, int idAdminCreador) {
        super(nombre, apellido, idUsuario, contrasena);
        this.turnoDeTrabajo = turnoDeTrabajo;
        this.idAdminCreador = idAdminCreador;
    }
}