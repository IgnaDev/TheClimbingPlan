package com.example.theclimbingplan;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(entity = Ejercicio.class,
        parentColumns = "idEjercicio",
        childColumns = "idEjercicio",
        onDelete = 5,
        onUpdate = 5)},
        indices = {@Index(value = {"nombre"},
        unique = true)})

public class Serie implements Serializable {

    @PrimaryKey
    @NonNull
    public String nombre;

    @NonNull
    public int idEjercicio;


    public int repeticiones;
    public float duracion;

    public float descansoCiclo;
    public int ciclos;

    @Ignore
    public Serie(int idEjercicio, String nombre, int repeticiones, float descansoCiclo, int ciclos) {
        this.idEjercicio = idEjercicio;
        this.nombre = nombre;
        this.repeticiones = repeticiones;
        this.descansoCiclo = descansoCiclo;
        this.ciclos = ciclos;
    }

    public Serie(int idEjercicio, String nombre, float duracion, float descansoCiclo, int ciclos) {
        this.idEjercicio = idEjercicio;
        this.nombre = nombre;
        this.duracion = duracion;
        this.descansoCiclo = descansoCiclo;
        this.ciclos = ciclos;
    }

    @Override
    public String toString() {
        return "Serie{" +
                ", idEjercicio=" + idEjercicio +
                ", nombre=" + nombre +
                ", repeticiones=" + repeticiones +
                ", duracion=" + duracion +
                ", descansoCiclo=" + descansoCiclo +
                ", ciclo=" + ciclos +
                '}';
    }



    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public float getDuracion() {
        return duracion;
    }

    public void setDuracion(float duracion) {
        this.duracion = duracion;
    }

    public float getDescansoCiclo() {
        return descansoCiclo;
    }

    public void setDescansoCiclo(float descansoCiclo) {
        this.descansoCiclo = descansoCiclo;
    }

    public int getCiclos() {
        return ciclos;
    }

    public void setCiclos(int ciclos) {
        this.ciclos = ciclos;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }
}
