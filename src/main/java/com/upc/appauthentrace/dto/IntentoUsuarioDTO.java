package com.upc.appauthentrace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntentoUsuarioDTO {
    private Long usuarioId;
    private String nombreUsuario;
    private long intentosFallidos;
}
