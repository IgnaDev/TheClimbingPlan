package com.example.theclimbingplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FormularioEntrenamiento extends AppCompatActivity {

    EditText etNombreEntrenamiento, etDescripcionEntrenamiento;
    Spinner spinnerCategoría;
    BaseDatos baseDatos;
    FloatingActionButton btnCrearCategoria, btnAddSerie;
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
        setTitle("Formulario Entrenamiento");
        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();
//TODO refrescar spinner al abrir activity

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

    public void irCrearCategoria(){
        Intent intent = new Intent(this, CrearCategoria.class);
        startActivity(intent);
    }
}