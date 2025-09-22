package com.upc.appauthentrace.service;

import com.upc.appauthentrace.dto.ReporteDTO;
import com.upc.appauthentrace.entidades.Grupo;
import com.upc.appauthentrace.entidades.Reporte;
import com.upc.appauthentrace.interfaces.IReporteServicio;
import com.upc.appauthentrace.repositorios.GrupoRepositorio;
import com.upc.appauthentrace.repositorios.ReporteRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteServicio implements IReporteServicio {

    @Autowired
    private ReporteRepositorio reporteRepositorio;

    @Autowired
    private GrupoRepositorio grupoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReporteDTO asignarReporteAGrupo(Long idReporte, Long idGrupo) {
        Reporte reporte = reporteRepositorio.findById(idReporte)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        Grupo grupo = grupoRepositorio.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        reporte.setIdGrupo(grupo);

        Reporte guardado = reporteRepositorio.save(reporte);
        return modelMapper.map(guardado, ReporteDTO.class);
    }
}
