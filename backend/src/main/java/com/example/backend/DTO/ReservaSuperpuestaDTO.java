package com.example.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaSuperpuestaDTO {
    private int idReserva;
    private String nombreProfesor;
    private String apellidoProfesor;
    private String correo;
    private String nombreCatedra;
    private int idProfesor;
    private int idCatedra;

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
