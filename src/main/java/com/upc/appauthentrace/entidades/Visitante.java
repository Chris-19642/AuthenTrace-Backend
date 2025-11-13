package com.upc.appauthentrace.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "visitantes")
public class Visitante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visitante", nullable = false)
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 50)
    private String nombre;

    @Column(name = "correo", nullable = false, length = 100)
    private String correo;

    @Column(name = "pais", nullable = false)
    private String pais;

    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @Column(name = "empresa", nullable = false, length = 50)
    private String empresa;

    @Column(name = "trabajo", nullable = false, length = 50)
    private String trabajo;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_correo", nullable = false)
    private LocalDateTime fechaCorreo = LocalDateTime.now();
}

