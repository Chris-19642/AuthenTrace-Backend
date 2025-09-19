package com.upc.appauthentrace.service;

import com.upc.appauthentrace.dto.RolDTO;
import com.upc.appauthentrace.dto.UsuarioDTO;
import com.upc.appauthentrace.entidades.Rol;
import com.upc.appauthentrace.entidades.Usuario;
import com.upc.appauthentrace.interfaces.IUsuarioServicio;
import com.upc.appauthentrace.repositorios.RolRepositorio;
import com.upc.appauthentrace.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioServicio implements IUsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Override
    public Usuario findById(Long id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }

    @Override
    public UsuarioDTO registrar(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getIdUsuario() == null) {
            Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);

            Rol rolUsuario = rolRepositorio.findByNombreRol("USUARIO")
                    .orElseThrow(() -> new RuntimeException("Rol USUARIO no encontrado"));

            usuario.setRol(rolUsuario);
            usuario.setFechaCreacion(LocalDate.now());

            Usuario guardado = usuarioRepositorio.save(usuario);

            UsuarioDTO dto = modelMapper.map(guardado, UsuarioDTO.class);
            dto.setRol(modelMapper.map(rolUsuario, RolDTO.class));
            return dto;
        }
        return null;
    }

    @Override
    @Transactional //sprint
    public UsuarioDTO editar(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepositorio.findById(usuarioDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setBloqueado(usuarioDTO.isBloqueado());

        Rol rol = rolRepositorio.findByNombreRol(usuarioDTO.getRol().getNombreRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);

        Usuario actualizado = usuarioRepositorio.save(usuario); //

        UsuarioDTO dto = modelMapper.map(actualizado, UsuarioDTO.class);
        dto.setRol(modelMapper.map(actualizado.getRol(), RolDTO.class));
        return dto;
    }


    @Override
    public UsuarioDTO bloquear(Long idUsuario) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setBloqueado(true);
        return modelMapper.map(usuarioRepositorio.save(usuario), UsuarioDTO.class);
    }

    @Override
    public void eliminar(Long idUsuario) {
        usuarioRepositorio.deleteById(idUsuario);
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepositorio.findAll()
                .stream()
                .map(usuario -> {
                    UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
                    dto.setRol(usuario.getRol() != null ? modelMapper.map(usuario.getRol(), RolDTO.class) : null);


                    return dto;
                })
                .toList();
    }

    @Override
    public UsuarioDTO buscarPorCorreo(String correo) {
        return modelMapper.map(usuarioRepositorio.findByCorreo(correo), UsuarioDTO.class);
    }

    @Override
    public List<UsuarioDTO> ordenarPorNombre() {
        return usuarioRepositorio.findAllByOrderByNombreAsc()
                .stream()
                .map(usuario -> {
                    UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
                    dto.setRol(usuario.getRol() != null ? modelMapper.map(usuario.getRol(), RolDTO.class) : null);
                    return dto;
                })
                .toList();
    }
    @Override
    public List<UsuarioDTO> buscarPorNombre(String nombre) {
        return usuarioRepositorio.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(usuario -> {
                    UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
                    dto.setRol(usuario.getRol() != null ? modelMapper.map(usuario.getRol(), RolDTO.class) : null);
                    return dto;
                })
                .toList();
    }

    @Override
    public List<UsuarioDTO> ordenarPorEstadoAsc() {
        return usuarioRepositorio.findAllByOrderByBloqueadoAsc()
                .stream()
                .map(usuario -> {
                    UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
                    dto.setRol(usuario.getRol() != null ? modelMapper.map(usuario.getRol(), RolDTO.class) : null);
                    return dto;
                })
                .toList();
    }

    @Override
    public List<UsuarioDTO> ordenarPorEstadoDesc() {
        return usuarioRepositorio.findAllByOrderByBloqueadoDesc()
                .stream()
                .map(usuario -> {
                    UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
                    dto.setRol(usuario.getRol() != null ? modelMapper.map(usuario.getRol(), RolDTO.class) : null);
                    return dto;
                })
                .toList();
    }

}
