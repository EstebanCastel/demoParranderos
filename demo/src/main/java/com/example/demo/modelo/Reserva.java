package com.example.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "reserva")
public class Reserva {

    public static final String SEQUENCE_NAME = "reservas_sequence";

    @Id
    private String id;

    private LocalDate fechaLlegada;
    private LocalDate fechaSalida;

    @DBRef
    private TipoHabitacion tipoHabitacion;

    private String titular;


}
