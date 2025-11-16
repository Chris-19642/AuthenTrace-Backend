package com.upc.appauthentrace.repositorios;

import com.upc.appauthentrace.entidades.ReporteVerificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReporteVerificacionRepositorio extends JpaRepository<ReporteVerificacion, Long> {

    List<ReporteVerificacion> findByUsuario_IdUsuario(Long idUsuario);
    List<ReporteVerificacion> findByDocumento_Id(Long idDocumento);
}
