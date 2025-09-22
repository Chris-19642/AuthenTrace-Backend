package com.upc.appauthentrace.interfaces;

import com.upc.appauthentrace.dto.DocumentoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDocumentoServicio {
    String subirDocumentoTemporal(MultipartFile file);
    boolean cancelarDocumentoTemporal(String nombreArchivo);

    DocumentoDTO guardarDefinitivo(String nombreArchivo, Long idUsuario);

    List<DocumentoDTO> listarDocumentos();
    List<DocumentoDTO> listarPorUsuario(Long idUsuario);
    void eliminarDocumento(Long idDocumento);
}
