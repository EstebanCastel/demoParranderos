// Servicio.java
package com.example.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "servicioProducto")
public class ServicioProducto {
    @Id
    private String id;

    @Field("nombre")
    private String nombre;

    @Field("precio")
    private int precio;

    @Field("esProducto")
    private boolean esProducto;

    @Field("duracion")
    private int duracion; // Cambiado a int para manejar valores nulos (opcional)

    public ServicioProducto() {
    }

    public ServicioProducto(String nombre, int precio, boolean esProducto, int duracion) {
        this.nombre = nombre;
        this.precio = precio;
        this.esProducto = esProducto;
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public boolean isEsProducto() {
        return esProducto;
    }

    public void setEsProducto(boolean esProducto) {
        this.esProducto = esProducto;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getId() {
        return id;
    }
}
