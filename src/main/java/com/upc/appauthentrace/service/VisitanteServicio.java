package com.upc.appauthentrace.service;

import com.upc.appauthentrace.dto.VisitanteDTO;
import com.upc.appauthentrace.entidades.Visitante;
import com.upc.appauthentrace.interfaces.IVisitanteServicio;
import com.upc.appauthentrace.repositorios.VisitanteRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitanteServicio implements IVisitanteServicio {

    @Autowired
    private VisitanteRepositorio visitanteRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public VisitanteDTO save(VisitanteDTO visitanteDTO) {
        Visitante  visitante = modelMapper.map(visitanteDTO, Visitante.class);
        visitante.setId(null);
        Visitante savedVisitante = visitanteRepositorio.save(visitante);
        return modelMapper.map(savedVisitante, VisitanteDTO.class);
    }
}
