package com.upc.appauthentrace.config;

import com.upc.appauthentrace.dto.RolDTO;
import com.upc.appauthentrace.dto.UsuarioDTO;
import com.upc.appauthentrace.entidades.Rol;
import com.upc.appauthentrace.entidades.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Mapea Usuario -> UsuarioDTO, personalizando el rol
        modelMapper.typeMap(Usuario.class, UsuarioDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getRol().getIdRol(), (dest, v) -> dest.getRol().setIdRol((Long) v));
            mapper.map(src -> src.getRol().getNombreRol(), (dest, v) -> dest.getRol().setNombreRol((String) v));
        });

        return modelMapper;
    }

}
