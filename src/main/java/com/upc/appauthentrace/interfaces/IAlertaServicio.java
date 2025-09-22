package com.upc.appauthentrace.interfaces;

import com.upc.appauthentrace.dto.AlertaDTO;
import com.upc.appauthentrace.entidades.Alerta;

import java.util.List;

public interface IAlertaServicio {
    public Alerta findById(Long id);
    public AlertaDTO registrarAlerta(AlertaDTO alertaDTO);
    public void eliminar(Long id);
    public List<AlertaDTO> listarAlertas();
    public List<AlertaDTO> listarAlertasPorUsuario(Long idUsuario);
    public List<AlertaDTO> listarAlertasPorEstado(String estado);
}