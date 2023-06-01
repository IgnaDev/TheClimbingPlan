package com.example.theclimbingplan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoEjercicio {
    @Query("SELECT nombre FROM Ejercicio ORDER BY nombre")
    List<String> consultarNombresEjercicio();

    @Query("SELECT * FROM Ejercicio ORDER BY nombre")
    List<Ejercicio> consultarTodosEjercicios();

    @Query("SELECT idEjercicio FROM Ejercicio WHERE nombre = :nombre")
    int consultarIDEjercicioPorNombre(String nombre);

    @Query("SELECT * FROM Ejercicio WHERE nombre = :nombre")
    Ejercicio consultarEjercicioPorNombre(String nombre);

    @Query("SELECT * FROM Ejercicio WHERE idGrupo = :idGrupo")
    List<Ejercicio> consultarEjercicioPorGrupo(int idGrupo);

    @Insert
    void insertarEjercicio(Ejercicio...ejercicios);

    @Query("UPDATE ejercicio SET descripcion = :descripcion, idGrupo = :idGrupo WHERE nombre = :nombre ")
    void actualizarEjercicio(String nombre, String descripcion, int idGrupo);

    @Query("DELETE FROM ejercicio WHERE nombre = :nombre")
    void eliminarEjercicio(String nombre);
}
