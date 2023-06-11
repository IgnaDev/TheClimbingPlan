package com.example.theclimbingplan.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index(value = {"nombre"},
        unique = true)})
public class Grupo implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int idGrupo;

    @NonNull
    public String nombre;

    public String descripcion;

    public Grupo(@NonNull String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    @Ignore
    public Grupo(int _tmpIdGrupo, @NonNull String nombre, String descripcion) {
        this.idGrupo=_tmpIdGrupo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return idGrupo + ";" +
                nombre;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
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
}
