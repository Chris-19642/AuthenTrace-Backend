package com.upc.appauthentrace.repositorios;

import com.upc.appauthentrace.entidades.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitanteRepositorio extends JpaRepository<Visitante, Long> {
}
