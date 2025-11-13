package com.upc.appauthentrace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitanteDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String pais;
    private String telefono;
    private String empresa;
    private String trabajo;
    private String mensaje;
}