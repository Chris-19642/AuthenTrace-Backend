package com.upc.appauthentrace.controllers;

import com.lowagie.text.DocumentException;
import com.upc.appauthentrace.dto.ReporteVerificacionDTO;
import com.upc.appauthentrace.interfaces.IReporteVerificacionServicio;
import com.upc.appauthentrace.service.PdfServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@PreAuthorize("hasRole('USER')")
public class ReporteVerificacionController {

    @Autowired
    private IReporteVerificacionServicio reporteServicio;
    @Autowired
    private PdfServicio pdfServicio;

    // Listar todos los reportes
    @GetMapping("/lista")
    public List<ReporteVerificacionDTO> listarReportes() {
        return reporteServicio.listarReportes();
    }

    // Listar reportes por usuario
    @GetMapping("/usuario/{idUsuario}")
    public List<ReporteVerificacionDTO> listarPorUsuario(@PathVariable Long idUsuario) {
        return reporteServicio.listarReportesPorUsuario(idUsuario);
    }

    // Listar reportes por documento
    @GetMapping("/documento/{idDocumento}")
    public List<ReporteVerificacionDTO> listarPorDocumento(@PathVariable Long idDocumento) {
        return reporteServicio.listarReportesPorDocumento(idDocumento);
    }

    @GetMapping("/descargar/{idReporte}")
    public ResponseEntity<byte[]> descargarReporte(@PathVariable Integer idReporte) {
        try {
            // Buscar el reporte por id
            ReporteVerificacionDTO reporte = reporteServicio.listarReportes()
                    .stream()
                    .filter(r -> r.getId().equals(idReporte))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("ReporteVerificacion no encontrado"));

            byte[] pdfBytes = pdfServicio.generarReportePDF(reporte);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_" + idReporte + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }
}

