package com.example.theclimbingplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class CrearSerie extends AppCompatActivity {

    Spinner spinnerGrupo;
    LinearLayout ejerLayout;
    BaseDatos baseDatos;
    List<Ejercicio> listaEjercicios = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_serie);
        ejerLayout = findViewById(R.id.ejerciciosLayout);
        spinnerGrupo = findViewById(R.id.categorySpinner);
        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();

        spinnerGrupo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String grupo = spinnerGrupo.getSelectedItem().toString();
                if (grupo == null) {
                    listaEjercicios = buscarTodosEjercicios();
                }
                else{
                    listaEjercicios = buscarEjercicioPorGrupo(grupo);
                }
            }
        });

    }
    private List<Ejercicio> buscarTodosEjercicios(){
        return baseDatos.daoEjercicio().consultarTodosEjercicios();
    }

    private List<Ejercicio> buscarEjercicioPorGrupo(String grupo){
            Grupo g = baseDatos.daoGrupo().consultarGruposPorNombre(grupo);
            return baseDatos.daoEjercicio().consultarEjercicioPorGrupo(g.getIdGrupo());
    }

    private void agregarEjercicios(List<Ejercicio> listaEjercicios){
        for(Ejercicio e : listaEjercicios){
            View
        }
    }
}