package com.upc.appauthentrace.interfaces;

import com.upc.appauthentrace.dto.SesionDTO;
import com.upc.appauthentrace.entidades.Sesione;

import java.time.LocalDate;
import java.util.List;

public interface ISesionServicio {
    Sesione findById(Long id);
    SesionDTO registrar(SesionDTO sesionDTO);
    SesionDTO editar(SesionDTO sesionDTO);
    void eliminar(Long id);
    List<SesionDTO> listarSesiones();
    List<SesionDTO> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);
}
