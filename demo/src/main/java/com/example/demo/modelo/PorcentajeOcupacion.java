package com.example.demo.modelo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PorcentajeOcupacion {

    private String habitacionId;
    private Double porcentajeOcupacion;

    // Constructor vacío
    public PorcentajeOcupacion() {}

    // Constructor con todos los campos
    public PorcentajeOcupacion(String habitacionId, Double porcentajeOcupacion) {
        this.habitacionId = habitacionId;
        this.porcentajeOcupacion = porcentajeOcupacion;
    }


    @Override
    public String toString() {
        return "PorcentajeOcupacion{" +
                "habitacionId='" + habitacionId + '\'' +
                ", porcentajeOcupacion=" + porcentajeOcupacion +
                '}';
    }
}

