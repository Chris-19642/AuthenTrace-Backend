package com.upc.appauthentrace.service;

import com.upc.appauthentrace.dto.IntentoFallidoDTO;
import com.upc.appauthentrace.entidades.Intentosfallido;
import com.upc.appauthentrace.entidades.Sesione;
import com.upc.appauthentrace.entidades.Usuario;
import com.upc.appauthentrace.interfaces.IIntentoFallidoServicio;
import com.upc.appauthentrace.repositorios.IntentoFallidoRepositorio;
import com.upc.appauthentrace.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntentoFallidoServicio implements IIntentoFallidoServicio {

    @Autowired
    private IntentoFallidoRepositorio intentoFallidoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public IntentoFallidoDTO registrarIntento(IntentoFallidoDTO intentoFallidoDTO) {
        Intentosfallido intentofallido = modelMapper.map(intentoFallidoDTO, Intentosfallido.class);
        return modelMapper.map(intentoFallidoRepositorio.save(intentofallido), IntentoFallidoDTO.class);
    }


    @Transactional
    @Override
    public void eliminar(Long id) {
        if (!intentoFallidoRepositorio.existsById(id)){
            throw new RuntimeException("Sesion no encontrado con ID " + id);
        }
        intentoFallidoRepositorio.deleteById(id);
    }

    @Override
    public Intentosfallido findById(Long id) {
        return intentoFallidoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Intento fallido no encontrado"));
    }

    @Override
    public List<IntentoFallidoDTO> listarIntentos() {
        return intentoFallidoRepositorio.findAll()
                .stream()
                .map(intentos -> modelMapper.map(intentos, IntentoFallidoDTO.class))
                .toList();
    }

    @Override
    public List<Intentosfallido> listarIntentosPorUsuario(Long idUsuario) {
        return intentoFallidoRepositorio.findByIdUsuario_IdUsuario(idUsuario);
    }

    @Override
    public List<IntentoFallidoDTO> listarIntentosPorIP(String ipOrigen) {
        return intentoFallidoRepositorio.findByIpOrigen(ipOrigen);
    }

}
