package com.upc.appauthentrace.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "reportes")
public class ReporteMensual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte", nullable = false)
    private Long idReporte;

    @Column(name = "mes", nullable = false)
    private int mes;

    @Column(name = "anio", nullable = false)
    private int anio;

    @Column(name = "total_sesiones", nullable = false)
    private long totalSesiones;

    @Column(name = "sesiones_activas", nullable = false)
    private long sesionesActivas;

    @Column(name = "total_intentos_fallidos", nullable = false)
    private long totalIntentosFallidos;

    @Column(name = "ips_principales", columnDefinition = "TEXT")
    private String ipsPrincipales; // Ejemplo: "192.168.0.1 (5), 10.0.0.2 (3)"

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_generacion", nullable = false)
    private Instant fechaGeneracion;

}