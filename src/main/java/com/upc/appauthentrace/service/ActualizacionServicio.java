package com.upc.appauthentrace.service;

import com.upc.appauthentrace.dto.ActualizacionDTO;
import com.upc.appauthentrace.entidades.Actualizacion;
import com.upc.appauthentrace.interfaces.IActualizacionServicio;
import com.upc.appauthentrace.repositorios.ActualizacionRepositorio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ActualizacionServicio implements IActualizacionServicio {

    @Autowired
    private ActualizacionRepositorio actualizacionRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public ActualizacionDTO programarActualizacion(ActualizacionDTO dto) {
        Actualizacion actualizacion = modelMapper.map(dto,Actualizacion.class);
        return modelMapper.map(actualizacionRepositorio.save(actualizacion),ActualizacionDTO.class); //map(actualizacionRepositorio.save(actualizacion), ActualizacionDTO.class);
    }

    @Override
    public List<ActualizacionDTO> listarActualizaciones() {
        return actualizacionRepositorio.findAll()
                .stream()
                .map(a -> modelMapper.map(a, ActualizacionDTO.class))
                .toList();
    }
}
