package com.upc.appauthentrace.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class IntentoFallidoDTO {
    @Column(name = "id_intento", nullable = false)
    private Integer id;

    private Long idUsuario;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @Column(name = "ip_origen", length = 45)
    private String ipOrigen;
}
