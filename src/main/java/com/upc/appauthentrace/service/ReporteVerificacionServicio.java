package com.upc.appauthentrace.service;

import com.lowagie.text.DocumentException;
import com.upc.appauthentrace.dto.ReporteVerificacionDTO;
import com.upc.appauthentrace.entidades.ReporteVerificacion;
import com.upc.appauthentrace.entidades.Documento;
import com.upc.appauthentrace.interfaces.IReporteVerificacionServicio;
import com.upc.appauthentrace.repositorios.ReporteVerificacionRepositorio;
import com.upc.appauthentrace.security.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteVerificacionServicio implements IReporteVerificacionServicio {

    @Autowired
    private ReporteVerificacionRepositorio reporteVerificacionRepositoro;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PdfServicio pdfServicio;

    @Override
    public List<ReporteVerificacionDTO> listarReportes() {
        return reporteVerificacionRepositoro.findAll().stream()
                .map(r -> modelMapper.map(r, ReporteVerificacionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReporteVerificacionDTO> listarReportesPorUsuario(Long idUsuario) {
        return reporteVerificacionRepositoro.findByUsuario_IdUsuario(idUsuario).stream()
                .map(r -> modelMapper.map(r, ReporteVerificacionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReporteVerificacionDTO> listarReportesPorDocumento(Long idDocumento) {
        return reporteVerificacionRepositoro.findByDocumento_Id(idDocumento).stream()
                .map(r -> modelMapper.map(r, ReporteVerificacionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void registrarReporteVerificacion(Long idUsuario, Long idDocumento, String estadoFirma) {
        try {
            ReporteVerificacion reporte = new ReporteVerificacion();
            reporte.setEstadoFirma(estadoFirma);
            reporte.setFechaGeneracion(Instant.now());

            // Asociar entidades con IDs
            User user = new User();
            user.setIdUsuario(idUsuario);
            reporte.setUsuario(user);

            Documento documento = new Documento();
            documento.setId(idDocumento);
            reporte.setDocumento(documento);

            // Guardar reporte primero para obtener ID
            reporteVerificacionRepositoro.save(reporte);

            // Generar DTO para PDF
            ReporteVerificacionDTO dto = modelMapper.map(reporte, ReporteVerificacionDTO.class);

            // Generar PDF
            byte[] pdfBytes = pdfServicio.generarReportePDF(dto);

            // Guardar PDF en disco (ruta /reportes/)
            String carpeta = "reportes";
            File dir = new File(carpeta);
            if (!dir.exists()) dir.mkdirs();

            String rutaPDF = carpeta + "/reporte_" + reporte.getId() + ".pdf";
            try (FileOutputStream fos = new FileOutputStream(rutaPDF)) {
                fos.write(pdfBytes);
            }

            // Actualizar ruta en el reporte
            reporte.setRutaReporte(rutaPDF);
            reporteVerificacionRepositoro.save(reporte);

        } catch (DocumentException | java.io.IOException e) {
            throw new RuntimeException("Error generando PDF del reporte", e);
        }
    }
}
