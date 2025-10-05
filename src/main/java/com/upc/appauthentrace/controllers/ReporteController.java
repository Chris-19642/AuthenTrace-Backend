package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.ReporteMensualDTO;
import com.upc.appauthentrace.interfaces.IReporteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
public class    ReporteController {

    @Autowired
    private IReporteServicio reporteServicio;

    @GetMapping("/mensual")
    public ReporteMensualDTO obtenerReporteMensual(@RequestParam int mes, @RequestParam int anio) {
        return reporteServicio.generarReporteMensual(mes, anio);
    }
}
