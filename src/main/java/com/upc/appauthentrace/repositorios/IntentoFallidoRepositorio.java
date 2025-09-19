package com.upc.appauthentrace.repositorios;

import com.upc.appauthentrace.dto.IntentoFallidoDTO;
import com.upc.appauthentrace.entidades.Intentosfallido;
import com.upc.appauthentrace.entidades.Sesione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IntentoFallidoRepositorio extends JpaRepository<Intentosfallido, Long> {
    List<Intentosfallido> findByIdUsuario_IdUsuario(Long idUsuario);
    List<IntentoFallidoDTO> findByIpOrigen(String ipOrigen);

}
