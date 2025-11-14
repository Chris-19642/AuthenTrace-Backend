package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.DocumentoDTO;
import com.upc.appauthentrace.interfaces.IDocumentoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    @Autowired
    private IDocumentoServicio documentoServicio;

    // 1. Subir temporal
    @PostMapping("/subir")
    @PreAuthorize("hasAuthority('USER')")
    public String subirDocumentoTemporal(@RequestParam("file") MultipartFile file) {
        return documentoServicio.subirDocumentoTemporal(file);
    }

    // 2. Cancelar temporal (botón X)
    @DeleteMapping("/cancelar/{nombreArchivo}")
    @PreAuthorize("hasRole('USER')")
    public String cancelarDocumentoTemporal(@PathVariable String nombreArchivo) {
        boolean eliminado = documentoServicio.cancelarDocumentoTemporal(nombreArchivo);
        return eliminado ? "Archivo eliminado" : "No existía el archivo";
    }


    // 3. Guardar definitivo al verificar firma
    @PostMapping("/guardar/{idUsuario}")
    @PreAuthorize("hasRole('USER')")
    public DocumentoDTO guardarDefinitivo(@RequestParam String nombreArchivo,
                                          @PathVariable Long idUsuario) {
        return documentoServicio.guardarDefinitivo(nombreArchivo, idUsuario);
    }

    // Listados
    @GetMapping("/lista")
    public List<DocumentoDTO> listar() {
        return documentoServicio.listarDocumentos();
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<DocumentoDTO> listarPorUsuario(@PathVariable Long idUsuario) {
        return documentoServicio.listarPorUsuario(idUsuario);
    }

    // Eliminar documento definitivo
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        documentoServicio.eliminarDocumento(id);
        return "Documento eliminado correctamente";
    }
}

