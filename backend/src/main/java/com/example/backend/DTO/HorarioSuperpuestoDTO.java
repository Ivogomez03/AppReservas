/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.DTO;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HorarioSuperpuestoDTO {
    private LocalTime horaInicio;
    private LocalTime horaFin;

    @Override
    public String toString() {
        return "HorarioSuperpuestoDTO{horaInicio=" + horaInicio + ", horaFin=" + horaFin + '}';
    }
}
