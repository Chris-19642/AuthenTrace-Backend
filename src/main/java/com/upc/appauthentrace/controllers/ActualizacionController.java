package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.ActualizacionDTO;
import com.upc.appauthentrace.interfaces.IActualizacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actualizaciones")
public class ActualizacionController {
    @Autowired
    private IActualizacionServicio actualizacionServicio;

    @PostMapping("/programar")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ActualizacionDTO> programarActualizacion(@RequestBody ActualizacionDTO dto){
        return ResponseEntity.ok(actualizacionServicio.programarActualizacion(dto));
    }
    @GetMapping("/listar")
    public ResponseEntity<List<ActualizacionDTO>> listarActualizaciones(){
        return ResponseEntity.ok(actualizacionServicio.listarActualizaciones());
    }
}
