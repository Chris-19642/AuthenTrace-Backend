package com.upc.appauthentrace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UsuarioDTO {
    private Long idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private boolean bloqueado;
    private RolDTO rol;
}
