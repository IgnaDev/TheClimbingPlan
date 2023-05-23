package com.example.theclimbingplan;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Categoria.class,
        parentColumns = "idCategoria",
        childColumns = "idCategoria",
        onDelete = 5,
        onUpdate = 5)},
        indices = {@Index(value = {"nombre"},
                unique = true)})

public class Sesion {
    @PrimaryKey(autoGenerate = true)
    public int idSesion;
    @NonNull
    public String nombre;

    public String descripcion;

    @NonNull
    public int idCategoria;


    public String calentamiento1;
    public String calentamiento2;
    public String calentamiento3;
    public String Serie1;
    public String Serie2;
    public String Serie3;
    public String Serie4;
    public String Serie5;
    public String Serie6;
    public String Serie7;
    public String Serie8;





}
