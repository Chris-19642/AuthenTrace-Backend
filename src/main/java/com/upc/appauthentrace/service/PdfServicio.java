package com.upc.appauthentrace.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.upc.appauthentrace.dto.ReporteVerificacionDTO;
import org.springframework.stereotype.Service;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class PdfServicio {

    public byte[] generarReportePDF(ReporteVerificacionDTO reporte) throws DocumentException {

        Document document = new Document(PageSize.A4, 50, 50, 70, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, out);

        document.open();

        // 🎨 PALETA (COLORES HEX CONVERTIDOS)
        Color primarioRojo = hexToColor("#BA181B");
        Color azulInstitucional = hexToColor("#161A1D");
        Color grisFondo = hexToColor("#D3D3D3");

        // 💠 FONDO DE PÁGINA
        PdfContentByte canvas = writer.getDirectContentUnder();
        Rectangle rect = new Rectangle(40, 40, 555, 802);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderColor(azulInstitucional);
        rect.setBorderWidth(2);
        rect.setBackgroundColor(grisFondo);
        canvas.rectangle(rect);

        // 🏛 LOGO (opcional, si existe)
        try {
            Image logo = Image.getInstance("src/main/resources/static/logo_at.png");
            logo.scaleToFit(85, 85);
            logo.setAlignment(Image.ALIGN_LEFT);
            document.add(logo);
        } catch (Exception ignored) {}

        // 🏷 TÍTULO
        Font tituloFont = new Font(Font.HELVETICA, 20, Font.BOLD, azulInstitucional);
        Paragraph title = new Paragraph("CERTIFICADO DE VERIFICACIÓN DOCUMENTAL", tituloFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("\n"));

        // 📋 SECCIÓN DE DATOS
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);
        table.setWidths(new float[]{35, 65});

        Font keyFont = new Font(Font.HELVETICA, 11, Font.BOLD, azulInstitucional);
        Font valFont = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.BLACK);

        addRow(table, "ID de Reporte:", String.valueOf(reporte.getId()), keyFont, valFont);
        addRow(table, "ID de Usuario:", String.valueOf(reporte.getIdUsuario()), keyFont, valFont);
        addRow(table, "ID de Documento:", String.valueOf(reporte.getIdDocumento()), keyFont, valFont);

        // Estado en rojo o verde fuerte según validez
        String estado = reporte.getEstadoFirma();
        Color estadoColor = estado != null && estado.equalsIgnoreCase("Válido") ? new Color(0,128,0) : primarioRojo;
        addRowColor(table, "Resultado:", estado != null ? estado : "No definido", keyFont,
                new Font(Font.HELVETICA, 11, Font.BOLD, estadoColor));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM, yyyy HH:mm", new Locale("es", "ES"))
                .withZone(ZoneId.of("America/Lima"));

        String fecha = reporte.getFechaGeneracion() != null
                ? formatter.format(reporte.getFechaGeneracion())
                : "Sin fecha";
        addRow(table, "Fecha de Emisión:", fecha, keyFont, valFont);

        document.add(table);

        document.add(new Paragraph("\n\n"));

        // ⚠ LEYENDA LEGAL
        Font notaFont = new Font(Font.HELVETICA, 9, Font.ITALIC, Color.GRAY);
        Paragraph nota = new Paragraph(
                "Este documento certifica el resultado emitido por el sistema AuthenTrace. "
                        + "Queda prohibida su manipulación, reproducción o alteración sin autorización.",
                notaFont);
        nota.setAlignment(Element.ALIGN_CENTER);
        document.add(nota);

        document.close();
        return out.toByteArray();
    }

    // 🔧 UTILIDAD: CONVERTIR HEX A Color
    private Color hexToColor(String hex) {
        return new Color(
                Integer.valueOf(hex.substring(1,3), 16),
                Integer.valueOf(hex.substring(3,5), 16),
                Integer.valueOf(hex.substring(5,7), 16)
        );
    }

    // 🔧 FILA NORMAL
    private void addRow(PdfPTable table, String key, String value, Font keyFont, Font valFont) {
        PdfPCell cell1 = new PdfPCell(new Phrase(key, keyFont));
        PdfPCell cell2 = new PdfPCell(new Phrase(value, valFont));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell2.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell1);
        table.addCell(cell2);
    }

    // 🔧 FILA COLOR PARA ESTADO
    private void addRowColor(PdfPTable table, String key, String value, Font keyFont, Font valFont) {
        PdfPCell cell1 = new PdfPCell(new Phrase(key, keyFont));
        PdfPCell cell2 = new PdfPCell(new Phrase(value, valFont));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell2.setBorder(Rectangle.NO_BORDER);
        cell2.setPadding(4);
        table.addCell(cell1);
        table.addCell(cell2);
    }
}
