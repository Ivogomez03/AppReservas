/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.DTO;


public class AulaConHorariosDTO extends AulaDTO {
    private HorarioSuperpuestoDTO horarioSuperpuesto;

    // Getters y setters
    public HorarioSuperpuestoDTO getHorarioSuperpuesto() {
        return horarioSuperpuesto;
    }

    public void setHorarioSuperpuesto(HorarioSuperpuestoDTO horarioSuperpuesto) {
        this.horarioSuperpuesto = horarioSuperpuesto;
    }
}

