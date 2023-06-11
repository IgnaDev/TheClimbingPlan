package com.example.theclimbingplan.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.theclimbingplan.Entities.Serie;

import java.util.List;

@Dao
public interface DaoSerie {

    @Query("SELECT * FROM Serie ORDER BY nombre")
    List<Serie> consultarTodasSeries();

    @Query("SELECT * FROM Serie Where nombre = :nombre")
    Serie consultarSeriePorNombre(String nombre);

    @Query("SELECT nombre FROM Serie ORDER BY nombre")
    List<String> consultarNombreSeries();

    @Insert
    void insertarSerie(Serie...series);

    @Query("DELETE FROM Serie Where nombre = :nombre ")
    void eliminarSerie(String nombre);

    @Query("UPDATE Serie SET repeticiones = :repeticiones, duracion = :duracion, descansoCiclo = :descansoCiclo, ciclos = :ciclos WHERE nombre = :nombre")
    void actualizarSesion(int repeticiones,float duracion, float descansoCiclo, String nombre, int ciclos);




}
