package com.example.theclimbingplan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DaoGrupo {

    @Insert
    void insertarGrupo(Grupo...grupos);

    @Query("SELECT * FROM Grupo")
    void consultarTodosGrupos();

    @Query("SELECT nombre FROM Grupo")
    void consultarNombresGrupos();

    @Query("SELECT * FROM Grupo WHERE nombre = :nombre")
    void consultarGruposPorNombre(String nombre);

    @Query("UPDATE Grupo SET descripcion =:descripcion WHERE nombre =:nombre")
    void actualizarGrupo(String nombre, String descripcion);

    @Query("DELETE FROM Grupo WHERE nombre =:nombre")
    void eliminarGrupo(String nombre);

}
