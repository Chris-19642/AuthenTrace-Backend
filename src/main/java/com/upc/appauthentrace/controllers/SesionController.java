package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.SesionDTO;
import com.upc.appauthentrace.entidades.Sesione;
import com.upc.appauthentrace.interfaces.ISesionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SesionController {
    @Autowired
    private ISesionServicio sesionServicio;
    @PostMapping("/sesion")
    public SesionDTO registrar(@RequestBody SesionDTO sesionDTO){
        return sesionServicio.registrar(sesionDTO);
    }
    @GetMapping("/sesion/{id}")
    public SesionDTO buscar(@PathVariable Long id) {
        Sesione sesion = sesionServicio.findById(id);
        if (sesion == null) {
            throw new RuntimeException("Sesión no encontrada");
        }

        SesionDTO dto = new SesionDTO();
        dto.setId(sesion.getId());
        dto.setIdUsuario(sesion.getIdUsuario().getIdUsuario());
        dto.setFechaInicio(sesion.getFechaInicio());
        dto.setFechaFin(sesion.getFechaFin());
        dto.setIpOrigen(sesion.getIpOrigen());
        dto.setNavegador(sesion.getNavegador());

        return dto;
    }


    @PutMapping("/sesion")
    public SesionDTO editar(@RequestBody SesionDTO sesionDTO) {return sesionServicio.editar(sesionDTO);};

    @DeleteMapping("sesion/{id}")
    public void eliminar(@PathVariable Long id) {sesionServicio.eliminar(id);};

    @GetMapping("/sesiones")
    public List<SesionDTO> listarSesiones() { return  sesionServicio.listarSesiones();};

    @GetMapping("/sesiones/rango-fechas")
    public List<SesionDTO> listarPorRangoFechas(@RequestParam("inicio") LocalDate fechaInicio, @RequestParam("fin") LocalDate fechaFin){
        return sesionServicio.listarPorRangoFechas(fechaInicio, fechaFin);
    }
}
