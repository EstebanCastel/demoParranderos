package com.example.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "estadia")
public class Estadia {

    public static final String SEQUENCE_NAME = "estadias_sequence";

    @Id
    private String id;

    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut;
    private boolean checkInRealizado;
    private boolean checkOutRealizado;

    @DBRef
    private Reserva reserva;

    public Estadia() {}
    
    public Estadia(LocalDate fechaCheckIn, LocalDate fechaCheckOut, boolean checkInRealizado, boolean checkOutRealizado, Reserva reserva) {
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
        this.checkInRealizado = checkInRealizado;
        this.checkOutRealizado = checkOutRealizado;
        this.reserva = reserva;
    }

}
