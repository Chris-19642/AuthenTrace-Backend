package com.upc.appauthentrace.service;

import com.upc.appauthentrace.dto.ReporteMensualDTO;
import com.upc.appauthentrace.entidades.Intentosfallido;
import com.upc.appauthentrace.entidades.Sesione;
import com.upc.appauthentrace.interfaces.IReporteServicio;
import com.upc.appauthentrace.repositorios.IntentoFallidoRepositorio;
import com.upc.appauthentrace.repositorios.SesionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
    public class ReporteServicio implements IReporteServicio {

    @Autowired
    private SesionRepositorio sesionRepositorio;

    @Autowired
    private IntentoFallidoRepositorio intentoFallidoRepositorio;

    @Override
    public ReporteMensualDTO generarReporteMensual(int mes, int anio) {
        YearMonth ym = YearMonth.of(anio, mes);
        LocalDate inicio = ym.atDay(1);
        LocalDate fin = ym.atEndOfMonth();

        List<Sesione> sesiones = sesionRepositorio.findByFechaInicioBetween(inicio, fin);
        List<Intentosfallido> intentos = intentoFallidoRepositorio.findAll();

        ReporteMensualDTO dto = new ReporteMensualDTO();
        dto.setMes(mes);
        dto.setAnio(anio);
        dto.setTotalSesiones(sesiones.size());

        long sesionesActivas = sesiones.stream().filter(s -> s.getFechaFin() == null).count();
        dto.setSesionesActivas(sesionesActivas);

        long intentosEnMes = intentos.stream()
                .filter(i -> {
                    Instant fecha = i.getFecha();
                    if (fecha != null) {
                        LocalDate fechaLocal = fecha.atZone(ZoneId.systemDefault()).toLocalDate();
                        return (!fechaLocal.isBefore(inicio)) && (!fechaLocal.isAfter(fin));
                    }
                    return false;
                }).count();
        dto.setTotalIntentosFallidos(intentosEnMes);

        Map<String, Long> conteoIps = sesiones.stream()
                .collect(Collectors.groupingBy(Sesione::getIpOrigen, Collectors.counting()));

        List<String> ipsPrincipales = conteoIps.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(e -> e.getKey() + " (" + e.getValue() + ")")
                .collect(Collectors.toList());

        dto.setIpsPrincipales(ipsPrincipales);

        return dto;
    }
}
