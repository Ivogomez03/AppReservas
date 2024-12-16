/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.DTO;

import com.example.backend.Modelos.Reserva;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AulaConHorariosDTO extends AulaDTO {
    private HorarioSuperpuestoDTO horarioSuperpuesto;
    private ReservaSuperpuestaDTO reservaSuperpuesta;

    @Override
    public String toString() {
        return "AulaConHorariosDTO{" +
                "horarioSuperpuesto=" + horarioSuperpuesto +
                ", reservaSuperpuesta=" + reservaSuperpuesta +
                '}';
    }
}
