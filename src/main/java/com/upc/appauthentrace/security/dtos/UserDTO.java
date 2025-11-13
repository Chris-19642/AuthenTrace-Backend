package com.upc.appauthentrace.security.dtos;

import lombok.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String username;
    private String password;
    private boolean bloqueado;
    private Set<RoleDTO> roles;
}