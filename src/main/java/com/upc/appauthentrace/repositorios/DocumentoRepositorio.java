package com.upc.appauthentrace.repositorios;

import com.upc.appauthentrace.entidades.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepositorio extends JpaRepository<Documento, Long> {
    List<Documento> findByUsuarioIdUsuario(Long idUsuario);
}
