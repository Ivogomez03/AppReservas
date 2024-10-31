package com.example.backend.Logica.CU13.Bedel;
import com.example.backend.Logica.usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity

@Table(name = "Bedel")
public class Bedel extends usuario {

    @Enumerated(EnumType.STRING)
    @Column(name = "Turno_de_trabajo")
    private TurnoDeTrabajo turnoDeTrabajo;

    @Column(name = "ID_admin_creador")
    private int idAdminCreador;

    public Bedel() {
        super();
    }

    public Bedel(String nombre, String apellido, int idUsuario, String contraseña, TurnoDeTrabajo turnoDeTrabajo, int idAdminCreador) {
        super(nombre, apellido, idUsuario, contraseña);
        this.turnoDeTrabajo = turnoDeTrabajo;
        this.idAdminCreador = idAdminCreador;
    }
}