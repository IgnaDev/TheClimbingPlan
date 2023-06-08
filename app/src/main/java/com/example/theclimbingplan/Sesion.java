package com.example.theclimbingplan;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(entity = Categoria.class,
        parentColumns = "idCategoria",
        childColumns = "idCategoria",
        onDelete = 5,
        onUpdate = 5)},
        indices = {@Index(value = {"nombre"},
                unique = true)})

public class Sesion implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int idSesion;
    @NonNull
    public String nombre;

    public String descripcion;

    @NonNull
    public int idCategoria;



    public Sesion(String nombre, String descripcion, int idCategoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
