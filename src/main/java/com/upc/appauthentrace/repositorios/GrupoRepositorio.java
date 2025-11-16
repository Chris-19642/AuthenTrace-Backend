package com.upc.appauthentrace.repositorios;

import com.upc.appauthentrace.entidades.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrupoRepositorio extends JpaRepository<Grupo, Long> {
    List<Grupo> findByIdUsuario_IdUsuario(Long idUsuario);
}
