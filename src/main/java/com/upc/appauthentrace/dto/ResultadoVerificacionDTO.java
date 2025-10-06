package com.upc.appauthentrace.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadoVerificacionDTO {
    private String mensaje;
    private double similitudPorcentaje;
    private boolean firmaValida;

    public ResultadoVerificacionDTO(String mensaje, double similitudPorcentaje, boolean firmaValida) {
        this.mensaje = mensaje;
        this.similitudPorcentaje = similitudPorcentaje;
        this.firmaValida = firmaValida;
    }
}
