package com.example.backend.Logica.CU13.Bedel;

import com.example.backend.Logica.usuario;

public class Bedel extends usuario {
    private TurnoDeTrabajo turno;
    private int ID_admin_creador;

    public TurnoDeTrabajo getTurno() {
        return turno;
    }

    public void setTurno(TurnoDeTrabajo turno) {
        this.turno = turno;
    }

    public int getIDAdminCreador() {
        return ID_admin_creador;
    }

    public void setIDAdminCreador(int ID_admin_creador) {
        this.ID_admin_creador = ID_admin_creador;
    }

    public Bedel(){
        super();
    }
    public Bedel(String nombre, String apellido, int id_usuario, String contraseña, TurnoDeTrabajo turno, int ID_admin_creador) {
        super(nombre, apellido, id_usuario, contraseña);
        this.turno = turno;
        this.ID_admin_creador = ID_admin_creador;
    }
}