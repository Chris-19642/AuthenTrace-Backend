package com.upc.appauthentrace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteMensualDTO {
    private int mes;
    private int anio;
    private long totalSesiones;
    private long sesionesActivas;
    private long totalIntentosFallidos;
    private List<String> ipsPrincipales;

}
