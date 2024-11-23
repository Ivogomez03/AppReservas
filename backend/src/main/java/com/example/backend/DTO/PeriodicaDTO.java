package com.example.backend.DTO;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PeriodicaDTO {

    private int cantAlumnos;
    private String tipoDeAula;
    private String nombre;
    private String apellido;
    private String nombreCatedra;
    private String Correo;
    private List<DiaPeriodicaDTO> diasPeriodicos;

}
