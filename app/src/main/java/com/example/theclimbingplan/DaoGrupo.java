package com.example.theclimbingplan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoGrupo {

    @Insert
    void insertarGrupo(Grupo...grupos);

    @Query("SELECT * FROM Grupo")
    List<Grupo> consultarTodosGrupos();

    @Query("SELECT nombre FROM Grupo")
    List<String> consultarNombresGrupos();

    @Query("SELECT * FROM Grupo WHERE nombre = :nombre")
    Grupo consultarGruposPorNombre(String nombre);

    @Query("UPDATE Grupo SET descripcion =:descripcion WHERE nombre =:nombre")
    void actualizarGrupo(String nombre, String descripcion);

    @Query("DELETE FROM Grupo WHERE nombre =:nombre")
    void eliminarGrupo(String nombre);

}
