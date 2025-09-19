package com.upc.appauthentrace.dto;

import com.upc.appauthentrace.entidades.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
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
