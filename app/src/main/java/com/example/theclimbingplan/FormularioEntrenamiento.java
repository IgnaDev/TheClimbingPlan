package com.example.theclimbingplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class FormularioEntrenamiento extends AppCompatActivity {

    EditText etNombreEntrenamiento, etDescripcionEntrenamiento;
    Spinner spinnerCategoría;
    BaseDatos baseDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_entrenamiento);
        etNombreEntrenamiento = findViewById(R.id.etNombreEntrenamiento);
        etDescripcionEntrenamiento = findViewById(R.id.etDescripcionEntrenamiento);
        spinnerCategoría = findViewById(R.id.spinnerCategoria);
        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();

        adapterSpinnerCategoria();


    }

    public void adapterSpinnerCategoria(){
        List<String> listaCategorias = new ArrayList<>();
        listaCategorias = baseDatos.daoCategoria().consultarNombresCategorias();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoría.setAdapter(adapter);
    }

    public void irEjercicio(View view){
        Intent intent = new Intent(this, MenuEjercicio.class);
        startActivity(intent);

    }
}