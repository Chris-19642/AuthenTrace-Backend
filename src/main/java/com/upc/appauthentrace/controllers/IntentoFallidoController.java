package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.IntentoFallidoDTO;
import com.upc.appauthentrace.dto.SesionDTO;
import com.upc.appauthentrace.entidades.Intentosfallido;
import com.upc.appauthentrace.entidades.Sesione;
import com.upc.appauthentrace.interfaces.IIntentoFallidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IntentoFallidoController {

    @Autowired
    private IIntentoFallidoServicio intentoFallidoServicio;

    @PostMapping("/intento")
    public IntentoFallidoDTO registrar(@RequestBody IntentoFallidoDTO intentoFallidoDTO){
        return intentoFallidoServicio.registrarIntento(intentoFallidoDTO);
    }

    @GetMapping("/intento/{id}")
    public Intentosfallido buscar(@PathVariable Long id) {return intentoFallidoServicio.findById(id);};


    @DeleteMapping("intento/{id}")
    public void eliminar(@PathVariable Long id) {intentoFallidoServicio.eliminar(id);};

    @GetMapping("/intentos")
    public List<IntentoFallidoDTO> listarIntentos() { return  intentoFallidoServicio.listarIntentos();};

    @GetMapping("/intentofallido/usuario/{id}")
    public List<Intentosfallido> listarIntentosPorUsuario(@PathVariable Long idUsuario) {
        return intentoFallidoServicio.listarIntentosPorUsuario(idUsuario);
    }
}
