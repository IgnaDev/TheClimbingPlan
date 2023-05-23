package com.example.theclimbingplan;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {Categoria.class, Ejercicio.class, Grupo.class, Historico.class, Serie.class, Sesion.class},
        version = 1
)
public abstract class BaseDatos extends RoomDatabase {
    public abstract DaoCategoria daoCategoria();
    public abstract DaoEjercicio daoEjercicio();
    public abstract DaoGrupo daoGrupo();
    public abstract DaoHistorico daoHistorico();
    public abstract DaoSerie daoSerie();
    public abstract DaoSesion daoSesion();
}
