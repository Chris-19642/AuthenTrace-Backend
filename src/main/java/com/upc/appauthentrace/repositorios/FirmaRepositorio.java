package com.upc.appauthentrace.repositorios;

import com.upc.appauthentrace.entidades.Firma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirmaRepositorio extends JpaRepository<Firma, Long> {
    boolean existsByHashFirma(String hashFirma); // para evitar duplicados
}
