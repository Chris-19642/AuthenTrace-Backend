package com.upc.appauthentrace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertaDTO {

    private Integer id;
    private String tipoActividad;
    private String descripcion;
    private Instant fecha;
    private String estado;
    private Long idUsuario;
    private String ipOrigen;
}