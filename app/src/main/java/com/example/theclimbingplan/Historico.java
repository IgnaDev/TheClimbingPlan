package com.example.theclimbingplan;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(foreignKeys = {@ForeignKey(entity = Sesion.class,
        parentColumns = "idSesion",
        childColumns = "idSesion",
        onDelete = 5,
        onUpdate = 5)},
        primaryKeys = {"idSesion", "fechaRealizacion"})

public class Historico implements Serializable {
    @NonNull
    public int idSesion;
    @NonNull
    public Date fechaRealizacion;
    public int puntuacion;
    public String observaciones;

    public Historico() {
    }
    public Historico(int idSesion, Date fechaRealizacion, int puntuacion, String observaciones) {
        this.idSesion = idSesion;
        this.fechaRealizacion = fechaRealizacion;
        this.puntuacion = puntuacion;
        this.observaciones = observaciones;
    }

    public Historico(int idSesion, Date fechaRealizacion, String observaciones) {
        this.idSesion = idSesion;
        this.fechaRealizacion = fechaRealizacion;
        this.observaciones = observaciones;
    }

    public Historico(int idSesion, Date fechaRealizacion, int puntuacion) {
        this.idSesion = idSesion;
        this.fechaRealizacion = fechaRealizacion;
        this.puntuacion = puntuacion;
    }

    public Historico(int idSesion, Date fechaRealizacion) {
        this.idSesion = idSesion;
        this.fechaRealizacion = fechaRealizacion;
    }
}
