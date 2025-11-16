package com.upc.appauthentrace.interfaces;

import com.upc.appauthentrace.dto.ActualizacionDTO;

import java.util.List;

public interface IActualizacionServicio {
    public ActualizacionDTO programarActualizacion(ActualizacionDTO dto);
    public List<ActualizacionDTO> listarActualizaciones();

}
