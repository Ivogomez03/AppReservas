package com.example.backend.Servicio;

import com.example.backend.DTO.LoginDTO;

public interface ILoginServicio {
    public int validarUsuario(LoginDTO loginDTO);
}
