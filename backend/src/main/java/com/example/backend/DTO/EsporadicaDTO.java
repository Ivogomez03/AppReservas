package com.example.backend.DTO;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class EsporadicaDTO {

    private int idReserva;
    private String nomrbeProfesor;
    private String apellidoProfesor;
    private String correo; // validar con exprecion regular
    private String nombreCatedra; 
    private String idProfesor;
    private String idCatedra;

}
