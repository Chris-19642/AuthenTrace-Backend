package com.upc.appauthentrace.service;

import com.upc.appauthentrace.dto.GrupoDTO;
import com.upc.appauthentrace.entidades.Grupo;
import com.upc.appauthentrace.interfaces.IGrupoServicio;
import com.upc.appauthentrace.repositorios.GrupoRepositorio;
import com.upc.appauthentrace.security.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrupoServicio implements IGrupoServicio {

    @Autowired
    private GrupoRepositorio grupoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GrupoDTO crearGrupo(GrupoDTO grupoDTO) {
        Grupo grupo = modelMapper.map(grupoDTO, Grupo.class);

        User user = new User();
        user.setIdUsuario(grupoDTO.getIdUsuario());
        grupo.setIdUsuario(user);

        Grupo guardado = grupoRepositorio.save(grupo);
        return modelMapper.map(guardado, GrupoDTO.class);
    }

    @Override
    public List<GrupoDTO> obtenerGruposPorUsuario(Long idUsuario) {
        List<Grupo> grupos = grupoRepositorio.findByIdUsuario_IdUsuario(idUsuario);
        return grupos.stream()
                .map(grupo -> modelMapper.map(grupo, GrupoDTO.class))
                .collect(Collectors.toList());
    }
}
