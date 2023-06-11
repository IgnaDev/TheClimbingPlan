package com.example.theclimbingplan.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(indices = {@Index(value = {"nombre"},
        unique = true)})
public class Categoria implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int idCategoria;
    @NonNull
    public String nombre;

    public String descripcion;

    public Categoria(@NonNull String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
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

    @Override
    public String toString() {
        return idCategoria + ";" + nombre;
    }
}
