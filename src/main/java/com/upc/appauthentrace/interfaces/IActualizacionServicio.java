package com.upc.appauthentrace.interfaces;

import com.upc.appauthentrace.dto.ActualizacionDTO;
import com.upc.appauthentrace.entidades.Actualizacion;
import com.upc.appauthentrace.entidades.Usuario;

public interface IActualizacionServicio {
    Actualizacion programarActualizacion(ActualizacionDTO dto, Usuario usuario);
}
