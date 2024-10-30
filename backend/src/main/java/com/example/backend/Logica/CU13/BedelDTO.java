package com.example.backend.Logica.CU13;

public class BedelDTO {
    private String nombre;
    private String apellido;
    private int idUsuario;
    private String contrasena;
    private TurnoDeTrabajo turnoDeTrabajo;
    private int ID_admin_creador;

    public BedelDTO() { }

    public BedelDTO(String nombre, String apellido, int idUsuario, String contrasena, TurnoDeTrabajo turnoDeTrabajo, int ID_admin_creador) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.idUsuario = idUsuario;
        this.contrasena = contrasena;
        this.turnoDeTrabajo = turnoDeTrabajo;
        this.ID_admin_creador = ID_admin_creador;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public TurnoDeTrabajo getTurnoDeTrabajo() { return turnoDeTrabajo; }
    public void setTurnoDeTrabajo(TurnoDeTrabajo turnoDeTrabajo) { this.turnoDeTrabajo = turnoDeTrabajo; }

    public int getIDAdminCreador() { return ID_admin_creador; }
    public void setIDAdminCreador(int ID_admin_creador) { this.ID_admin_creador = ID_admin_creador; }
}

