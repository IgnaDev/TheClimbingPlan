package com.example.theclimbingplan;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoSerieSesion {
    @Insert
    void insertarSerieSesion(SerieSesion serieSesion);

    @Delete
    void eliminarSerieSesion(SerieSesion serieSesion);

    @Query("SELECT * FROM SerieSesion")
    List<SerieSesion> obtenerTodasLasSerieSesion();

    @Query("SELECT * FROM SerieSesion WHERE nombreSerie = :nombreSerie")
    List<SerieSesion> obtenerSesionesPorSerie(String nombreSerie);

    @Query("SELECT * FROM SerieSesion WHERE idSesion = :idSesion")
    List<SerieSesion> obtenerSeriesPorSesion(int idSesion);

    @Update
    void actualizarSerieSesion(SerieSesion serieSesion);

    @Update
    void actualizarSerieSesion(List<SerieSesion> serieSesiones);

}
