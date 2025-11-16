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
public class DocumentoDTO {
    private Long id;
    private Long idUsuario;
    private String nombreArchivo;
    private String tipoDocumento;
    private String rutaArchivo;
    private Instant fechaSubida;

}
