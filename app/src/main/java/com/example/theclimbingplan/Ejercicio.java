package com.example.theclimbingplan;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Grupo.class,
        parentColumns = "idGrupo",
        childColumns = "idGrupo",
        onDelete = 5,
        onUpdate = 5)},
        indices = {@Index(value = {"nombre"},
                unique = true)})
public class Ejercicio {

    @PrimaryKey(autoGenerate = true)
    public int idEjercicio;
    @NonNull
    public String nombre;

    public String descripcion;
    public int idGrupo;


    public Ejercicio(@NonNull String nombre, String descripcion, int idGrupo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idGrupo = idGrupo;
    }

    @Ignore
    public Ejercicio(@NonNull String nombre) {
        this.nombre = nombre;
    }
    @Ignore
    public Ejercicio() {

    }

    @Override
    public String toString() {
        return "Ejercicio{" +
                "idEjercicio=" + idEjercicio +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", grupo='" + idGrupo + '\'' +
                '}';
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

    public int getGrupo() {
        return idGrupo;
    }

    public void setGrupo(int grupo) {
        this.idGrupo = grupo;
    }
}
