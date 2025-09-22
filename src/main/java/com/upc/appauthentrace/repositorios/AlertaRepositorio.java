package com.upc.appauthentrace.repositorios;

import com.upc.appauthentrace.entidades.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepositorio extends JpaRepository<Alerta, Long> {
    List<Alerta> findByUsuarioAfectado_IdUsuario(Long idUsuario);
    List<Alerta> findByEstado(String estado);
    List<Alerta> findByIpOrigen(String ipOrigen);
}
