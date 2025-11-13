package com.upc.appauthentrace.service;

import com.upc.appauthentrace.dto.SesionDTO;
import com.upc.appauthentrace.entidades.Sesione;
import com.upc.appauthentrace.interfaces.ISesionServicio;
import com.upc.appauthentrace.repositorios.SesionRepositorio;
import com.upc.appauthentrace.security.entities.User;
import com.upc.appauthentrace.security.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SesionServicio implements ISesionServicio {
    @Autowired
    private SesionRepositorio sesionRepositorio;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Sesione findById(Long idSesion) {
        return sesionRepositorio.findById(idSesion).orElse(null);
    }

    @Override
    public SesionDTO registrar(SesionDTO sesionDTO) {
        Sesione sesion = modelMapper.map(sesionDTO, Sesione.class);

        User user = userRepository.findById(sesionDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID " + sesionDTO.getIdUsuario()));

        sesion.setIdUsuario(user);

        return modelMapper.map(sesionRepositorio.save(sesion), SesionDTO.class);
    }

    @Override
    public SesionDTO editar(SesionDTO sesionDTO) {
        return sesionRepositorio.findById(sesionDTO.getId().longValue())
                .map(existing -> {
                    Sesione sesion = modelMapper.map(sesionDTO, Sesione.class);

                    User user = userRepository.findById(sesionDTO.getIdUsuario())
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID " + sesionDTO.getIdUsuario()));
                    sesion.setIdUsuario(user);

                    Sesione guardado = sesionRepositorio.save(sesion);
                    return modelMapper.map(guardado, SesionDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Sesion con ID " + sesionDTO.getId() + " no encontrado"));
    }

    @Override
    public List<SesionDTO> listarSesiones() {
        return sesionRepositorio.findAll().stream()
                .map(sesion -> {
                    SesionDTO dto = modelMapper.map(sesion, SesionDTO.class);
                    dto.setIdUsuario(sesion.getIdUsuario().getIdUsuario());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        if (!sesionRepositorio.existsById(id)) {
            throw new RuntimeException("Sesion no encontrada con ID " + id);
        }
        sesionRepositorio.deleteById(id);
    }

    @Override
    public List<SesionDTO> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return sesionRepositorio.findByFechaInicioBetween(fechaInicio, fechaFin).stream()
                .map(sesion -> {
                    SesionDTO dto = modelMapper.map(sesion, SesionDTO.class);
                    dto.setIdUsuario(sesion.getIdUsuario().getIdUsuario());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
