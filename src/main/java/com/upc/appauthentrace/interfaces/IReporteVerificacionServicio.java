package com.upc.appauthentrace.interfaces;

import com.upc.appauthentrace.dto.ReporteVerificacionDTO;

import java.util.List;

public interface IReporteVerificacionServicio {
    public List<ReporteVerificacionDTO> listarReportes();
    public List<ReporteVerificacionDTO> listarReportesPorUsuario(Long idUsuario);
    public List<ReporteVerificacionDTO> listarReportesPorDocumento(Long idDocumento);
    public void registrarReporteVerificacion(Long idUsuario, Long idDocumento, String estadoFirma);

}
