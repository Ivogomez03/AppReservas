/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.DTO;

import com.example.backend.Modelos.Reserva;

public class AulaConHorariosDTO extends AulaDTO {
    private HorarioSuperpuestoDTO horarioSuperpuesto;
    private ReservaSuperpuestaDTO reservaSuperpuesta;

    // Getters y setters
    public HorarioSuperpuestoDTO getHorarioSuperpuesto() {
        return horarioSuperpuesto;
    }

    public void setHorarioSuperpuesto(HorarioSuperpuestoDTO horarioSuperpuesto) {
        this.horarioSuperpuesto = horarioSuperpuesto;
    }

    public ReservaSuperpuestaDTO getReservaSuperpuesta() {
        return reservaSuperpuesta;
    }

    public void setReservaSuperpuesta(ReservaSuperpuestaDTO reservaSuperpuesta) {
        this.reservaSuperpuesta = reservaSuperpuesta;
    }

    @Override
    public String toString() {
        return "AulaConHorariosDTO{" +
                "horarioSuperpuesto=" + horarioSuperpuesto +
                ", reservaSuperpuesta=" + reservaSuperpuesta +
                '}';
    }
}

