package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.ActualizacionDTO;
import com.upc.appauthentrace.entidades.Actualizacion;
import com.upc.appauthentrace.entidades.Usuario;
import com.upc.appauthentrace.interfaces.IActualizacionServicio;
import com.upc.appauthentrace.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/actualizaciones")
public class ActualizacionController {
    @Autowired
    private IActualizacionServicio actualizacionServicio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/programar")
    public ResponseEntity<Actualizacion> programarActualizacion(
            @RequestBody ActualizacionDTO dto){
        Usuario usuario = usuarioRepositorio.findById(dto.getIdUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Actualizacion actualizacion = actualizacionServicio.programarActualizacion(dto, usuario);
        return ResponseEntity.ok(actualizacion);
    }

}
