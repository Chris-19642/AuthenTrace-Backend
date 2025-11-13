package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.ResultadoVerificacionDTO;
import com.upc.appauthentrace.service.VerificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verificacion")
public class VerificacionController {

    @Autowired
    private VerificacionServicio verificacionServicio;

    @GetMapping("/firma")
    public ResponseEntity<ResultadoVerificacionDTO> verificarFirma(
            @RequestParam Long idDocumento,
            @RequestParam Long idUsuario) {

        ResultadoVerificacionDTO resultado = verificacionServicio.verificarFirma(idDocumento, idUsuario);
        return ResponseEntity.ok(resultado);
    }
}
