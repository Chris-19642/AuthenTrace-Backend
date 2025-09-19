package com.upc.appauthentrace.interfaces;

import com.upc.appauthentrace.dto.UsuarioDTO;
import com.upc.appauthentrace.entidades.Usuario;

import java.util.List;

public interface IUsuarioServicio {
    public Usuario findById(Long id);
    public UsuarioDTO registrar(UsuarioDTO usuarioDTO);
    public UsuarioDTO editar(UsuarioDTO usuarioDTO);
    public UsuarioDTO bloquear(Long idUsuario);
    public void eliminar(Long id);
    public List<UsuarioDTO> listarUsuarios();
    public UsuarioDTO buscarPorCorreo(String correo);
    List<UsuarioDTO> ordenarPorNombre();
    List<UsuarioDTO> buscarPorNombre(String nombre);
    List<UsuarioDTO> ordenarPorEstadoAsc();
    List<UsuarioDTO> ordenarPorEstadoDesc();
}
