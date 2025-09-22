package com.upc.appauthentrace.interfaces;

import com.upc.appauthentrace.dto.ReporteDTO;

public interface IReporteServicio {
    ReporteDTO asignarReporteAGrupo(Long idReporte, Long idGrupo);
}
