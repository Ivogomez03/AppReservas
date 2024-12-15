package com.example.backend.DTO;

public class ReservaSuperpuestaDTO {
    private int idReserva;
    private String nombreProfesor;
    private String apellidoProfesor;
    private String correo;
    private String nombreCatedra;
    private int idProfesor;
    private int idCatedra;

    // Getters y setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getApellidoProfesor() {
        return apellidoProfesor;
    }

    public void setApellidoProfesor(String apellidoProfesor) {
        this.apellidoProfesor = apellidoProfesor;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreCatedra() {
        return nombreCatedra;
    }

    public void setNombreCatedra(String nombreCatedra) {
        this.nombreCatedra = nombreCatedra;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public int getIdCatedra() {
        return idCatedra;
    }

    public void setIdCatedra(int idCatedra) {
        this.idCatedra = idCatedra;
    }

    @Override
    public String toString() {
        return "ReservaSuperpuestaDTO{" +
                "idReserva=" + idReserva +
                ", nombreProfesor='" + nombreProfesor + '\'' +
                ", apellidoProfesor='" + apellidoProfesor + '\'' +
                ", correo='" + correo + '\'' +
                ", nombreCatedra='" + nombreCatedra + '\'' +
                ", idProfesor=" + idProfesor +
                ", idCatedra=" + idCatedra +
                '}';
    }
}
