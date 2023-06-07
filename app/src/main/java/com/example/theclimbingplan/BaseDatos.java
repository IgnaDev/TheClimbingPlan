package com.example.theclimbingplan;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {Categoria.class, Ejercicio.class, Grupo.class, Historico.class, Serie.class, Sesion.class, SerieSesion.class},
        version = 4,
        exportSchema = true,
        autoMigrations = {
                @AutoMigration(from = 3, to = 4)}
)
@TypeConverters({Converters.class})
public abstract class BaseDatos extends RoomDatabase {
    public abstract DaoCategoria daoCategoria();
    public abstract DaoEjercicio daoEjercicio();
    public abstract DaoGrupo daoGrupo();
    public abstract DaoHistorico daoHistorico();
    public abstract DaoSerie daoSerie();
    public abstract DaoSesion daoSesion();
    public abstract DaoSerieSesion daoSerieSesion();
}
