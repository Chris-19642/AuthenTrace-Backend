package com.upc.appauthentrace.config;

import com.upc.appauthentrace.security.dtos.UserDTO;
import com.upc.appauthentrace.security.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(User.class, UserDTO.class).addMappings(mapper -> {});


 /* Mapea Usuario -> anterior"UsuarioDTO", personalizando el rol
       /*modelMapper.typeMap(Usuario.class, UsuarioDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getRol().getIdRol(), (dest, v) -> dest.getRol().setIdRol((Long) v));
            mapper.map(src -> src.getRol().getNombreRol(), (dest, v) -> dest.getRol().setNombreRol((String) v));
        });*/

        return modelMapper;
    }

}
