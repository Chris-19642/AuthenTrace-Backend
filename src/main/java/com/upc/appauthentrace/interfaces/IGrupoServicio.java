package com.upc.appauthentrace.interfaces;

import com.upc.appauthentrace.dto.GrupoDTO;

import java.util.List;

public interface IGrupoServicio {
    GrupoDTO crearGrupo(GrupoDTO grupoDTO);
    List<GrupoDTO> obtenerGruposPorUsuario(Long idUsuario);
}
