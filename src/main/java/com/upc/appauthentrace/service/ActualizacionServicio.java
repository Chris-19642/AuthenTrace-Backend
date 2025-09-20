package com.upc.appauthentrace.service;

import com.upc.appauthentrace.dto.ActualizacionDTO;
import com.upc.appauthentrace.entidades.Actualizacion;
import com.upc.appauthentrace.entidades.Usuario;
import com.upc.appauthentrace.interfaces.IActualizacionServicio;
import com.upc.appauthentrace.repositorios.ActualizacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class ActualizacionServicio implements IActualizacionServicio {

    @Autowired
    private ActualizacionRepositorio actualizacionRepositorio;

    @Override
    public Actualizacion programarActualizacion(ActualizacionDTO dto, Usuario usuario) {
        if (usuario.getRol() == null || !usuario.getRol().getNombreRol().equalsIgnoreCase("ADMIN")) {
            try {
                throw new AccessDeniedException("Solo administradores pueden programar actualizaciones");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }
        Actualizacion actualizacion = new Actualizacion();
        actualizacion.setVersion(dto.getVersion());
        actualizacion.setFechaProgramada(dto.getFechaProgramada());
        return actualizacionRepositorio.save(actualizacion);
    }
}
