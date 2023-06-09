package com.example.theclimbingplan.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.theclimbingplan.Entities.Sesion;

import java.util.List;

@Dao
public interface DaoSesion {
    @Insert
    void insertarSesion(Sesion...sesiones);

    @Query("SELECT * FROM Sesion")
    List<Sesion> consultarTodasSesiones();

    @Query("SELECT nombre FROM Sesion")
    List<String> consultarNombreSesiones();

    @Query("SELECT * FROM Sesion WHERE nombre =:nombre")
    Sesion consultarSesionesPorNombre(String nombre);

    @Query("SELECT idSesion FROM Sesion WHERE nombre =:nombre")
    int consultaridPorNombre(String nombre);

    @Query("SELECT * FROM Sesion WHERE idCategoria =:idCategoria")
    List<Sesion> consultarSesionesPorCategoria(int idCategoria);

    @Query("UPDATE Sesion SET idCategoria =:idCategoria WHERE nombre =:nombre")
    void actualizarCategoria(String nombre, int idCategoria);

    @Query("UPDATE Sesion SET descripcion =:descripcion WHERE nombre =:nombre")
    void actualizarCategoria(String nombre, String descripcion);

    @Query("DELETE FROM Sesion WHERE nombre =:nombre")
    void eliminarSesion(String nombre);

}
