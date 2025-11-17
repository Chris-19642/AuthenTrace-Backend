package com.upc.appauthentrace.service;

import com.upc.appauthentrace.dto.ResultadoVerificacionDTO;
import com.upc.appauthentrace.entidades.Documento;
import com.upc.appauthentrace.entidades.Firma;
import com.upc.appauthentrace.repositorios.DocumentoRepositorio;
import com.upc.appauthentrace.repositorios.FirmaRepositorio;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class VerificacionServicio {

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Autowired
    private FirmaRepositorio firmaRepositorio;

    @Autowired
    private ReporteVerificacionServicio reporteVerificacionServicio;

    private static final double UMBRAL_SIMILITUD = 0.75;

    public ResultadoVerificacionDTO verificarFirma(Long idDocumento, Long idUsuario) {
        try {
            Documento doc = documentoRepositorio.findById(idDocumento).orElse(null);
            if (doc == null) {
                return new ResultadoVerificacionDTO("Documento no encontrado", 0, false);
            }

            File pdfFile = new File(doc.getRutaArchivo());
            if (!pdfFile.exists()) {
                return new ResultadoVerificacionDTO("No se encontró el archivo PDF", 0, false);
            }

            BufferedImage documentoImagen = pdfToImage(pdfFile);
            if (documentoImagen == null) {
                return new ResultadoVerificacionDTO("Error al convertir el PDF a imagen", 0, false);
            }

            // Convertir PDF a Mat y escala de grises
            Mat docMat = bufferedImageToMat(documentoImagen);
            opencv_imgproc.cvtColor(docMat, docMat, opencv_imgproc.COLOR_BGR2GRAY);

            // Detectar región posible de firma
            Mat thresh = new Mat();
            opencv_imgproc.threshold(docMat, thresh, 180, 255, opencv_imgproc.THRESH_BINARY_INV);

            MatVector contours = new MatVector();
            Mat hierarchy = new Mat();
            opencv_imgproc.findContours(thresh.clone(), contours, hierarchy,
                    opencv_imgproc.RETR_EXTERNAL, opencv_imgproc.CHAIN_APPROX_SIMPLE);

            Rect firmaRect = null;
            double maxArea = 0;
            for (long i = 0; i < contours.size(); i++) {
                Rect rect = opencv_imgproc.boundingRect(new Mat(contours.get(i)));
                if (rect.area() > maxArea && rect.area() > 2000) {
                    maxArea = rect.area();
                    firmaRect = rect;
                }
            }

            // Si no hay firma detectada
            if (firmaRect == null) {
                reporteVerificacionServicio.registrarReporteVerificacion(idUsuario, idDocumento, "Vacio");
                return new ResultadoVerificacionDTO("No se detectó ninguna firma", 0, false);
            }

            Mat regionFirma = new Mat(docMat, firmaRect);
            opencv_imgproc.resize(regionFirma, regionFirma, new Size(300, 100));

            List<Firma> firmas = firmaRepositorio.findAll();
            if (firmas.isEmpty()) {
                reporteVerificacionServicio.registrarReporteVerificacion(idUsuario, idDocumento, "No válida");
                return new ResultadoVerificacionDTO("No hay firmas registradas en la base de datos", 0, false);
            }

            double mejorCoincidencia = 0.0;
            for (Firma firma : firmas) {
                if (firma.getFirma() == null) continue;

                Mat firmaBD = opencv_imgcodecs.imdecode(new Mat(new BytePointer(firma.getFirma())), opencv_imgcodecs.IMREAD_GRAYSCALE);
                if (firmaBD.empty()) continue;

                opencv_imgproc.resize(firmaBD, firmaBD, new Size(300, 100));
                double similitud = compararFirmas(regionFirma, firmaBD);
                mejorCoincidencia = Math.max(mejorCoincidencia, similitud);
            }

            double porcentaje = mejorCoincidencia * 100;
            boolean firmaValida = mejorCoincidencia >= UMBRAL_SIMILITUD;
            String mensaje = firmaValida ? "Firma Válida" : "Firma No Válida";
            String estadoFirma = firmaValida ? "Válido" : "No válido";

            // Registrar reporte
            reporteVerificacionServicio.registrarReporteVerificacion(idUsuario, idDocumento, estadoFirma);

            return new ResultadoVerificacionDTO(mensaje, porcentaje, firmaValida);

        } catch (Exception e) {
            return new ResultadoVerificacionDTO("Error durante la verificación: " + e.getMessage(), 0, false);
        }
    }

    // --- Conversión PDF a BufferedImage ---
    private BufferedImage pdfToImage(File pdfFile) {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFRenderer renderer = new PDFRenderer(document);
            return renderer.renderImageWithDPI(0, 150);
        } catch (IOException e) {
            return null;
        }
    }

    // --- Conversión BufferedImage a Mat ---
    private Mat bufferedImageToMat(BufferedImage bi) {
        if (bi == null) return new Mat();
        BufferedImage imageRGB = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = imageRGB.createGraphics();
        g.drawImage(bi, 0, 0, null);
        g.dispose();
        byte[] pixels = ((java.awt.image.DataBufferByte) imageRGB.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(imageRGB.getHeight(), imageRGB.getWidth(), opencv_core.CV_8UC3);
        mat.data().put(pixels);
        return mat;
    }

    // --- Comparación de firmas ---
    private double compararFirmas(Mat img1, Mat img2) {
        Mat gray1 = new Mat();
        Mat gray2 = new Mat();

        if (img1.channels() == 3)
            opencv_imgproc.cvtColor(img1, gray1, opencv_imgproc.COLOR_BGR2GRAY);
        else
            gray1 = img1.clone();

        if (img2.channels() == 3)
            opencv_imgproc.cvtColor(img2, gray2, opencv_imgproc.COLOR_BGR2GRAY);
        else
            gray2 = img2.clone();

        opencv_imgproc.resize(gray1, gray1, new Size(300, 100));
        opencv_imgproc.resize(gray2, gray2, new Size(300, 100));

        Mat edges1 = new Mat();
        Mat edges2 = new Mat();
        opencv_imgproc.Canny(gray1, edges1, 50, 150);
        opencv_imgproc.Canny(gray2, edges2, 50, 150);

        Mat iguales = new Mat();
        opencv_core.compare(edges1, edges2, iguales, opencv_core.CMP_EQ);

        double pixelesIguales = opencv_core.countNonZero(iguales);
        double totalPixeles = gray1.size().width() * gray1.size().height();
        double similitud = pixelesIguales / totalPixeles;

        return Math.max(0, Math.min(similitud, 1));
    }
}
