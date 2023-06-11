package com.example.theclimbingplan.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class SerieSesion implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int idSerieSesion;
    @NonNull
    public String nombreSerie;
    @NonNull
    public int idSesion;

    public SerieSesion(@NonNull String nombreSerie, int idSesion) {
        this.nombreSerie = nombreSerie;
        this.idSesion = idSesion;
    }

    public String getNombreSerie() {
        return nombreSerie;
    }

    public void setIdSerie(String nombreSerie) {
        this.nombreSerie = nombreSerie;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }
}
