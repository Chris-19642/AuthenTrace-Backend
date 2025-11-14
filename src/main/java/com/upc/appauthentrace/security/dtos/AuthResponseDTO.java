package com.upc.appauthentrace.security.dtos;

import java.util.Set;

@lombok.Data
public class AuthResponseDTO {
    private String jwt;
    private Set<String> roles;
    private Long idUsuario;
}
