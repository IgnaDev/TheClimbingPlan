package com.example.theclimbingplan.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.theclimbingplan.DataBase.BaseDatos;
import com.example.theclimbingplan.Entities.Ejercicio;
import com.example.theclimbingplan.Entities.Grupo;
import com.example.theclimbingplan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CrearSerie extends AppCompatActivity {

    Spinner spinnerGrupo;
    LinearLayout ejerLayout;
    BaseDatos baseDatos;
    List<Ejercicio> listaEjercicios = new ArrayList<>();
    FloatingActionButton btnCrearEjercicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_serie);
        ejerLayout = findViewById(R.id.ejerciciosLayout);
        spinnerGrupo = findViewById(R.id.categorySpinner);
        btnCrearEjercicio = findViewById(R.id.btnAddEjercicio);
        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();
        setTitle("Crear Serie");
        Intent intent = getIntent();
        ArrayList<String> listaSeries =  intent.getStringArrayListExtra("listaSeries");


        adapterSpinnerGrupo();
        spinnerGrupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String grupo = spinnerGrupo.getSelectedItem().toString();
                    listaEjercicios = buscarEjercicioPorGrupo(grupo);
                    inflarVistaEjercicios(listaEjercicios, listaSeries);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                listaEjercicios = buscarTodosEjercicios();
                inflarVistaEjercicios(listaEjercicios, listaSeries);
            }
        });

        btnCrearEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irFormularioEjercicio(listaSeries);
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

    private void inflarVistaEjercicios(List<Ejercicio> listaEjercicios, ArrayList<String> listaSeries){
        ejerLayout.removeAllViews();
        for(Ejercicio e : listaEjercicios){
            // Inflar la vista del ejercicio desde un archivo XML de diseño
            View ejerView = getLayoutInflater().inflate(R.layout.item_ejercicio, null);

            // Configurar la vista de la ejercicio con los datos correspondientes
            TextView tvNombreEjer = ejerView.findViewById(R.id.tvNombreEjer);
            tvNombreEjer.setText(e.getNombre());

            // Obtener referencia al botón flotante "agregar"
            FloatingActionButton btnAddEjer = ejerView.findViewById(R.id.btnAddEjer);
            btnAddEjer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombreEjercicio = e.nombre;
                    elegirEjercicio(nombreEjercicio, listaSeries);
                }
            });

            // Añadir la vista de la ejercicio al LinearLayout
            ejerLayout.addView(ejerView);
        }
    }

    public void elegirEjercicio(String nombreEjercicio, ArrayList<String> listaSeries){
        Intent intent = new Intent(CrearSerie.this, FormularioSerie.class);
        Bundle bundle = new Bundle();
        bundle.putString("nombre", nombreEjercicio);
        bundle.putStringArrayList("listaSeries", listaSeries);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void irFormularioEjercicio(ArrayList<String> listaSeries){
        Intent intent = new Intent(CrearSerie.this, FormularioEjercicio.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("listaSeries", listaSeries);
        intent.putExtras(bundle);
        startActivity(intent);
    }



    public void adapterSpinnerGrupo(){
        List<String> listaGrupos = new ArrayList<>();
        listaGrupos = baseDatos.daoGrupo().consultarNombresGrupos();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaGrupos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrupo.setAdapter(adapter);
    }

}