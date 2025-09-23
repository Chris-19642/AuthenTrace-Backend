package com.upc.appauthentrace.interfaces;

import com.upc.appauthentrace.dto.ReporteMensualDTO;

public interface IReporteServicio {
    ReporteMensualDTO generarReporteMensual(int mes, int anio);
}
