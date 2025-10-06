package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.FirmaDTO;
import com.upc.appauthentrace.interfaces.IFirmaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/firmas")
public class FirmaController {

    @Autowired
    private IFirmaServicio firmaServicio;

    // Subir firma base
    @PostMapping("/subir")
    public String subirFirma(@RequestParam("file") MultipartFile file) {
        return firmaServicio.registrarFirmaBase(file);
    }

    // Listar todas las firmas base
    @GetMapping("/lista")
    public List<FirmaDTO> listarFirmas() {
        return firmaServicio.listarFirmas();
    }

    // Eliminar una firma
    @DeleteMapping("/eliminar/{id}")
    public void eliminarFirma(@PathVariable Long id) {
        firmaServicio.eliminarFirma(id);
    }
}
