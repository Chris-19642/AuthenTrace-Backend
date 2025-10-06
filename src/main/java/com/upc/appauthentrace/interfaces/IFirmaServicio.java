package com.upc.appauthentrace.interfaces;

import com.upc.appauthentrace.dto.FirmaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFirmaServicio {
    String registrarFirmaBase(MultipartFile file);
    List<FirmaDTO> listarFirmas();
    void eliminarFirma(Long idFirma);
}