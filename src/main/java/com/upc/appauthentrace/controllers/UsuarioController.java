package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.dto.RolDTO;
import com.upc.appauthentrace.dto.UsuarioDTO;
import com.upc.appauthentrace.entidades.Usuario;
import com.upc.appauthentrace.interfaces.IUsuarioServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UsuarioController {
    @Autowired
    private IUsuarioServicio usuarioServicio;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/usuario")
    public UsuarioDTO registrar(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioServicio.registrar(usuarioDTO);
    }

    @GetMapping("/usuarios")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioServicio.listarUsuarios();
    }

    @GetMapping("/buscar/{id}")
    public UsuarioDTO buscar(@PathVariable Long id) {
        Usuario usuario = usuarioServicio.findById(id);
        if (usuario == null) {
            return null;
        }
        return modelMapper.map(usuario, UsuarioDTO.class);
    }



    @PutMapping("/usuario")
    public UsuarioDTO editar(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioServicio.editar(usuarioDTO);
    }


    @PutMapping("/bloquear/{id}")
    public UsuarioDTO bloquear(@PathVariable Long id) {
        return usuarioServicio.bloquear(id);
    }

    @DeleteMapping("/usuario/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioServicio.eliminar(id);
    }

    @GetMapping("/correo/{correo}")
    public UsuarioDTO buscarPorCorreo(@PathVariable String correo) {
        return usuarioServicio.buscarPorCorreo(correo);
    }

    @GetMapping("/ordenados")
    public List<UsuarioDTO> ordenarPorNombre() {
        return usuarioServicio.ordenarPorNombre();
    }

    @GetMapping("/nombre/buscar")
    public List<UsuarioDTO> buscarPorNombre(@RequestParam String nombre) {
        return usuarioServicio.buscarPorNombre(nombre);
    }

    @GetMapping("/ordenar/estado/asc")
    public List<UsuarioDTO> ordenarEstadoAsc() {
        return usuarioServicio.ordenarPorEstadoAsc();
    }

    @GetMapping("/ordenar/estado/desc")
    public List<UsuarioDTO> ordenarEstadoDesc() {
        return usuarioServicio.ordenarPorEstadoDesc();
    }
}
