package com.upc.appauthentrace.security.interfaces;

import com.upc.appauthentrace.security.dtos.UserDTO;
import com.upc.appauthentrace.security.entities.Role;
import com.upc.appauthentrace.security.entities.User;

import java.util.List;

public interface IUserService {
    public void save(User user);
    public void grabar(Role role);
    public Integer insertUserRol(Long user_id, Long rol_id);


    /*Usados anteriormente en Usuario*/
    public User findById(Long idUsuario);
    public UserDTO registrar(UserDTO userDTO);
    public UserDTO editarPerfilUsuario(UserDTO userDTO);
    public UserDTO editarAdmin(UserDTO userDTO);
    public UserDTO bloquear(Long idUsuario);
    public void eliminar(Long idUsuario);
    public List<UserDTO> listarUsuarios();
    public UserDTO buscarPorCorreo(String correo);
}
