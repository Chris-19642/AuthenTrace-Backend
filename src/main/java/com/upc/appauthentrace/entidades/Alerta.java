package com.upc.appauthentrace.entidades;

import com.upc.appauthentrace.security.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "alertas")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta", nullable = false)
    private Integer id;

    @Column(name = "tipo_actividad", nullable = false, length = 100)
    private String tipoActividad;
    // Ejemplo: "INTENTOS_FALLIDOS", "CAMBIO_NO_AUTORIZADO", etc.

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;
    // Detalles de la alerta: cuenta afectada, IP, etc.

    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "PENDIENTE";
    // PENDIENTE, INVESTIGANDO, RESUELTA

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private User usuarioAfectado;
    // Puede ser null si la alerta no está asociada a un usuario específico

    @Column(name = "ip_origen", length = 45)
    private String ipOrigen;

}