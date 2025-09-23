package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.GrupoDTO;
import com.upc.appauthentrace.interfaces.IGrupoServicio;
import com.upc.appauthentrace.service.GrupoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController {
    @Autowired
    private IGrupoServicio grupoServicio;

    @PostMapping("/crear")
    public ResponseEntity<GrupoDTO> crearGrupo(@RequestBody GrupoDTO dto) {
        GrupoDTO nuevoGrupo = grupoServicio.crearGrupo(dto);
        return ResponseEntity.ok(nuevoGrupo);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<GrupoDTO>> obtenerGruposPorUsuario(@PathVariable Long idUsuario) {
        List<GrupoDTO> grupos = grupoServicio.obtenerGruposPorUsuario(idUsuario);
        return ResponseEntity.ok(grupos);
    }
}
