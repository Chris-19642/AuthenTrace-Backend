package com.upc.appauthentrace.dto;

import com.upc.appauthentrace.entidades.Rol;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class RolDTO {
    private Long idRol;
    private String nombreRol;
}
