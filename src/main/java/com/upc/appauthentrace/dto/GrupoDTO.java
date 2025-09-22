package com.upc.appauthentrace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrupoDTO {
    private Long id;
    private Long idUsuario;
    private String nombre;
}
