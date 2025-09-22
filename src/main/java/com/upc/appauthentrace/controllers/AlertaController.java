package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.AlertaDTO;
import com.upc.appauthentrace.entidades.Alerta;
import com.upc.appauthentrace.interfaces.IAlertaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AlertaController {

    @Autowired
    private IAlertaServicio alertaServicio;

    @PostMapping("/alerta")
    public AlertaDTO registrar(@RequestBody AlertaDTO alertaDTO) {
        return alertaServicio.registrarAlerta(alertaDTO);
    }

    @GetMapping("/alerta/{id}")
    public Alerta buscar(@PathVariable Long id) {
        return alertaServicio.findById(id);
    }

    @DeleteMapping("/alerta/{id}")
    public void eliminar(@PathVariable Long id) {
        alertaServicio.eliminar(id);
    }

    @GetMapping("/alertas")
    public List<AlertaDTO> listarAlertas() {
        return alertaServicio.listarAlertas();
    }

    @GetMapping("/alertas/usuario/{idUsuario}")
    public List<AlertaDTO> listarPorUsuario(@PathVariable Long idUsuario) {
        return alertaServicio.listarAlertasPorUsuario(idUsuario);
    }

    @GetMapping("/alertas/estado/{estado}")
    public List<AlertaDTO> listarPorEstado(@PathVariable String estado) {
        return alertaServicio.listarAlertasPorEstado(estado);
    }
}