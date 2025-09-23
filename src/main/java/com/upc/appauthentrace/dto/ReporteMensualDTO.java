package com.upc.appauthentrace.dto;

import java.util.List;

public class ReporteMensualDTO {
    private int mes;
    private int anio;
    private long totalSesiones;
    private long sesionesActivas;
    private long totalIntentosFallidos;
    private List<String> ipsPrincipales;

    public ReporteMensualDTO() {}

    public int getMes() { return mes; }
    public void setMes(int mes) { this.mes = mes; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public long getTotalSesiones() { return totalSesiones; }
    public void setTotalSesiones(long totalSesiones) { this.totalSesiones = totalSesiones; }

    public long getSesionesActivas() { return sesionesActivas; }
    public void setSesionesActivas(long sesionesActivas) { this.sesionesActivas = sesionesActivas; }

    public long getTotalIntentosFallidos() { return totalIntentosFallidos; }
    public void setTotalIntentosFallidos(long totalIntentosFallidos) { this.totalIntentosFallidos = totalIntentosFallidos; }

    public List<String> getIpsPrincipales() { return ipsPrincipales; }
    public void setIpsPrincipales(List<String> ipsPrincipales) { this.ipsPrincipales = ipsPrincipales; }
}
