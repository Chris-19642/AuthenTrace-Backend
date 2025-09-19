package com.upc.appauthentrace.interfaces;

import com.upc.appauthentrace.dto.IntentoFallidoDTO;
import com.upc.appauthentrace.dto.SesionDTO;
import com.upc.appauthentrace.entidades.Intentosfallido;
import com.upc.appauthentrace.entidades.Sesione;

import java.util.List;

public interface IIntentoFallidoServicio {
    public Intentosfallido findById(Long id);
    public IntentoFallidoDTO registrarIntento(IntentoFallidoDTO intentoFallidoDTO);
    public void eliminar(Long id);
    public List<IntentoFallidoDTO> listarIntentos();
    public List<Intentosfallido> listarIntentosPorUsuario(Long idUsuario);
    public List<IntentoFallidoDTO> listarIntentosPorIP(String ipOrigen);
}
