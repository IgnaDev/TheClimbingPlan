package com.example.theclimbingplan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FormularioEntrenamiento extends AppCompatActivity {

    EditText etNombreEntrenamiento, etDescripcionEntrenamiento;
    Spinner spinnerCategoría;
    BaseDatos baseDatos;
    LinearLayout serieLayout;
    FloatingActionButton btnCrearCategoria, btnAddSerie;
    //private static final int CREATE_SERIE_REQUEST = 1;
    private List<Serie> seriesList = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_entrenamiento);
        etNombreEntrenamiento = findViewById(R.id.etNombreEntrenamiento);
        etDescripcionEntrenamiento = findViewById(R.id.etDescripcionEntrenamiento);
        spinnerCategoría = findViewById(R.id.spinnerCategoria);
        btnCrearCategoria = findViewById(R.id.btnCrearCategoria);
        btnAddSerie = findViewById(R.id.btnAddSerie);
        serieLayout = findViewById(R.id.seriesLayout);
        setTitle("Formulario Entrenamiento");
        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();
//TODO botón eliminar serie
        //TODO añadir serie existente
        //TODO crear tabla intermedia serie sesion
        getBundleSerie(seriesList);
        //CATEGORIA
        adapterSpinnerCategoria();
        btnCrearCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irCrearCategoria();
            }
        });

        //SERIES
        btnAddSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irCrearSerie();
            }
        });

    }

    public void adapterSpinnerCategoria(){
        List<String> listaCategorias = new ArrayList<>();
        listaCategorias = baseDatos.daoCategoria().consultarNombresCategorias();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoría.setAdapter(adapter);
    }

    public void irCrearSerie(){
        Intent intent = new Intent(this, CrearSerie.class);
        startActivity(intent);
    }

    public void getBundleSerie(List<Serie> seriesList) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                Serie serie = (Serie) bundle.getSerializable("serie");
                seriesList.add(serie);
                agregarSeries(seriesList);
            }
        }

    public void irCrearCategoria(){
        Intent intent = new Intent(this, CrearCategoria.class);
        startActivity(intent);
    }

    private void agregarSeries(List<Serie> listaSeries){
        for(Serie e : listaSeries){
            // Inflar la vista del Serie desde un archivo XML de diseño
            View serieView = getLayoutInflater().inflate(R.layout.item_serie, null);

            // Configurar la vista de la Serie con los datos correspondientes
            TextView tvNombreSerie = serieView.findViewById(R.id.tvNombreSerie);
            tvNombreSerie.setText(e.nombre);

            // Obtener referencia al botón flotante "borrar"
            FloatingActionButton btnDeleteSerie = serieView.findViewById(R.id.btnDeleteSerie);
            btnDeleteSerie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listaSeries.remove(e);
                    recreate();
                }
            });

            // Añadir la vista de la ejercicio al LinearLayout
            serieLayout.addView(serieView);
        }
    }
}