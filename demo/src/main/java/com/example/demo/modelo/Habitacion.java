package com.example.demo.modelo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document(collection = "habitacion")
public class Habitacion {

    @Id
    private String id;

    @Field("costo")
    private double costo;

    @DBRef
    private TipoHabitacion tipoHabitacion;

    public Habitacion() {}

    public Habitacion(double costo, TipoHabitacion tipoHabitacion) {
        this.costo = costo;
        this.tipoHabitacion = tipoHabitacion;
    }
}

