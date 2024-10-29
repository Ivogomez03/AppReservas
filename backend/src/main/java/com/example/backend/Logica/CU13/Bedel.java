package com.example.backend.Logica.CU13;

import com.example.backend.Logica.usuario;

public class Bedel extends usuario {
    private TurnoDeTrabajo turno;

    public TurnoDeTrabajo getTurno() {
        return turno;
    }

    public void setTurno(TurnoDeTrabajo turno) {
        this.turno = turno;
    }

    public Bedel(String nombre, String apellido, int id_usuario, String contraseña, TurnoDeTrabajo turno) {
        super(nombre, apellido, id_usuario, contraseña);
        this.turno = turno;
    }
}
