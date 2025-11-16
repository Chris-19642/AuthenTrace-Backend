package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.VisitanteDTO;
import com.upc.appauthentrace.interfaces.IVisitanteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VisitanteController {
    @Autowired
    private IVisitanteServicio visitanteServicio;

    @PostMapping("/visitantes")
    public ResponseEntity<VisitanteDTO> save(@RequestBody VisitanteDTO visitanteDTO) {
        VisitanteDTO savedVisitante = visitanteServicio.save(visitanteDTO);
        return ResponseEntity.ok(savedVisitante);
    }
}