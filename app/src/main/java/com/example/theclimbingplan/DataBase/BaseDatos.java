package com.example.theclimbingplan.DataBase;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.theclimbingplan.Converters;
import com.example.theclimbingplan.Daos.DaoCategoria;
import com.example.theclimbingplan.Daos.DaoEjercicio;
import com.example.theclimbingplan.Daos.DaoGrupo;
import com.example.theclimbingplan.Daos.DaoHistorico;
import com.example.theclimbingplan.Daos.DaoSerie;
import com.example.theclimbingplan.Daos.DaoSerieSesion;
import com.example.theclimbingplan.Daos.DaoSesion;
import com.example.theclimbingplan.Entities.Ejercicio;
import com.example.theclimbingplan.Entities.Categoria;
import com.example.theclimbingplan.Entities.Grupo;
import com.example.theclimbingplan.Entities.Historico;
import com.example.theclimbingplan.Entities.Serie;
import com.example.theclimbingplan.Entities.SerieSesion;
import com.example.theclimbingplan.Entities.Sesion;

@Database(
        entities = {Categoria.class, Ejercicio.class, Grupo.class, Historico.class, Serie.class, Sesion.class, SerieSesion.class},
        version = 5,
        exportSchema = true
        //autoMigrations = {
                //@AutoMigration(from = 4, to = 5)}
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
