package com.upc.appauthentrace.service;

import com.upc.appauthentrace.dto.AlertaDTO;
import com.upc.appauthentrace.entidades.Alerta;
import com.upc.appauthentrace.interfaces.IAlertaServicio;
import com.upc.appauthentrace.repositorios.AlertaRepositorio;
import com.upc.appauthentrace.security.entities.User;
import com.upc.appauthentrace.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AlertaServicio implements IAlertaServicio {

    @Autowired
    private AlertaRepositorio alertaRepositorio;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Alerta findById(Long id) {
        return alertaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada con ID " + id));
    }

    @Override
    public AlertaDTO registrarAlerta(AlertaDTO alertaDTO) {
        Alerta alerta = modelMapper.map(alertaDTO, Alerta.class);

        // Si viene idUsuario, lo buscamos y lo asignamos
        if (alertaDTO.getIdUsuario() != null) {
            User user = userRepository.findById(alertaDTO.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID " + alertaDTO.getIdUsuario()));
            alerta.setUsuarioAfectado(user);
        }

        // Asignar fecha actual si no viene en el DTO
        if (alerta.getFecha() == null) {
            alerta.setFecha(Instant.now());
        }

        return modelMapper.map(alertaRepositorio.save(alerta), AlertaDTO.class);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        if (!alertaRepositorio.existsById(id)) {
            throw new RuntimeException("Alerta no encontrada con ID " + id);
        }
        alertaRepositorio.deleteById(id);
    }

    @Override
    public List<AlertaDTO> listarAlertas() {
        return alertaRepositorio.findAll()
                .stream()
                .map(alerta -> modelMapper.map(alerta, AlertaDTO.class))
                .toList();
    }

    @Override
    public List<AlertaDTO> listarAlertasPorUsuario(Long idUsuario) {
        return alertaRepositorio.findByUsuarioAfectado_IdUsuario(idUsuario)
                .stream()
                .map(alerta -> modelMapper.map(alerta, AlertaDTO.class))
                .toList();
    }

    @Override
    public List<AlertaDTO> listarAlertasPorEstado(String estado) {
        return alertaRepositorio.findByEstado(estado)
                .stream()
                .map(alerta -> modelMapper.map(alerta, AlertaDTO.class))
                .toList();
    }
}
