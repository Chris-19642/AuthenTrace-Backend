package com.upc.appauthentrace.repositorios;

import com.upc.appauthentrace.entidades.Sesione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface SesionRepositorio extends JpaRepository<Sesione, Long> {
    List<Sesione> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);
}

