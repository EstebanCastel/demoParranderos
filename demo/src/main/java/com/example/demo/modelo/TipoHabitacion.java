package com.example.demo.modelo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document(collection = "tipoHabitacion")
public class TipoHabitacion {

    public static final String SEQUENCE_NAME = "tipo_habitaciones_sequence";

    @Id
    private String id;

    @Field("nombre")
    private String nombre;

    @Field("capacidad")
    private int capacidad;

    @Field("dotacion")
    private String dotacion;

    public TipoHabitacion() {}

    public TipoHabitacion(String nombre, int capacidad, String dotacion) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.dotacion = dotacion;
    }
}
