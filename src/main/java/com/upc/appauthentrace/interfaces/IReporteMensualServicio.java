package com.upc.appauthentrace.interfaces;

import com.upc.appauthentrace.dto.ReporteMensualDTO;

public interface IReporteMensualServicio {
    ReporteMensualDTO generarReporteMensual(int mes, int anio);
}
