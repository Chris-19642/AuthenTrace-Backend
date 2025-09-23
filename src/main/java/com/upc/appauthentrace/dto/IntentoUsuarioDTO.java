package com.upc.appauthentrace.dto;

public class IntentoUsuarioDTO {
    private Long usuarioId;
    private String nombreUsuario;
    private long intentosFallidos;

    public IntentoUsuarioDTO() {}

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public long getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(long intentosFallidos) { this.intentosFallidos = intentosFallidos; }
}
