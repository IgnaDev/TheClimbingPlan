package com.example.theclimbingplan.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.theclimbingplan.Entities.Categoria;

import java.util.List;

@Dao
public interface DaoCategoria {
    @Query("SELECT nombre FROM Categoria")
    List<String> consultarNombresCategorias();

    @Query("SELECT * FROM Categoria")
    List<Categoria> consultarTodasCategorias();

    @Query("SELECT * FROM Categoria WHERE nombre = :nombre")
    Categoria consultarCategoriasPorNombre(String nombre);

    @Insert
    void insertarCategoria(Categoria...categorias);

    @Query("UPDATE Categoria SET descripcion =:descripcion WHERE nombre =:nombre")
    void actualizarCategoria(String nombre, String descripcion);

    @Query("DELETE FROM Categoria WHERE nombre =:nombre")
    void eliminarCategoria(String nombre);


}
