package com.upc.appauthentrace.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "firmas")
public class Firma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_firma", nullable = false)
    private Long id;

    // Firma almacenada en binario (imagen JPG/PNG de la firma)
    @Lob
    @Column(name = "firma", nullable = false)
    private byte[] firma;

    // Hash de la firma (para identificar rápidamente duplicados o validaciones simples)
    @Column(name = "hash_firma", nullable = false, length = 200)
    private String hashFirma;

    // Fecha de registro de la firma en la BD
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_registro", nullable = false)
    private Instant fechaRegistro;

}