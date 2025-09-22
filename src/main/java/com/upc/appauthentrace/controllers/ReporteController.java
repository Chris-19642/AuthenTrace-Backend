package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.ReporteDTO;
import com.upc.appauthentrace.interfaces.IReporteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    @Autowired
    private IReporteServicio reporteServicio;

    @PostMapping("/asignar-grupo")
    public ResponseEntity<ReporteDTO> asignarReporteAGrupo(@RequestParam Long idReporte, @RequestParam Long idGrupo) {
        ReporteDTO reporte = reporteServicio.asignarReporteAGrupo(idReporte, idGrupo);
        return ResponseEntity.ok(reporte);
    }
}
