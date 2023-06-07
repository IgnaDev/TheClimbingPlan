package com.example.theclimbingplan;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"nombreSerie", "idSesion"},
        foreignKeys = {
                @ForeignKey(entity = Serie.class, parentColumns = "nombre", childColumns = "nombreSerie"),
                @ForeignKey(entity = Sesion.class, parentColumns = "idSesion", childColumns = "idSesion")
        })
public class SerieSesion {
    @NonNull
    public String nombreSerie;
    @NonNull
    public int idSesion;

    public SerieSesion(String nombreSerie, int idSesion) {
        this.nombreSerie = nombreSerie;
        this.idSesion = idSesion;
    }

    public String nombreSerie() {
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
