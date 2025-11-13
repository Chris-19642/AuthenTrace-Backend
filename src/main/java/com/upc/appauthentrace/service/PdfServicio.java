package com.upc.appauthentrace.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.upc.appauthentrace.dto.ReporteVerificacionDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfServicio {

    public byte[] generarReportePDF(ReporteVerificacionDTO reporte) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        // Título
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph titulo = new Paragraph("ReporteMensual de Documento", font);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(Chunk.NEWLINE);

        // Contenido
        document.add(new Paragraph("ID ReporteMensual: " + reporte.getId()));
        document.add(new Paragraph("Usuario ID: " + reporte.getIdUsuario()));
        document.add(new Paragraph("Documento ID: " + reporte.getIdDocumento()));
        document.add(new Paragraph("Grupo ID: " + reporte.getIdGrupo()));
        document.add(new Paragraph("Estado Firma: " + reporte.getEstadoFirma()));
        document.add(new Paragraph("Fecha Generación: " + reporte.getFechaGeneracion()));

        document.close();
        return out.toByteArray();
    }
}
