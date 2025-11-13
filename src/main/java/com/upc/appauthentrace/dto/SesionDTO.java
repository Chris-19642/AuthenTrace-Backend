package com.upc.appauthentrace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SesionDTO {
    private Integer id;
    private Long idUsuario;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String ipOrigen;
    private String navegador;
}
