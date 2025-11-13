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
public class ReporteVerificacionDTO {
    private Integer id;
    private Long idUsuario;
    private Long idDocumento;
    private Long idGrupo;
    private String estadoFirma;
    private Instant fechaGeneracion;
    private String rutaReporte;
}
