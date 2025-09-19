package com.upc.appauthentrace.repositorios;

import com.upc.appauthentrace.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    List<Usuario> findAllByOrderByNombreAsc();
    public Usuario findByCorreo(String correo);
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    List<Usuario> findAllByOrderByBloqueadoAsc();
    List<Usuario> findAllByOrderByBloqueadoDesc();
}
