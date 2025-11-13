package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.ReporteMensualDTO;
import com.upc.appauthentrace.interfaces.IReporteMensualServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
public class ReporteMensualController {

    @Autowired
    private IReporteMensualServicio reporteServicio;

    @GetMapping("/mensual")
    public ReporteMensualDTO obtenerReporteMensual(@RequestParam int mes, @RequestParam int anio) {
        return reporteServicio.generarReporteMensual(mes, anio);
    }
}
