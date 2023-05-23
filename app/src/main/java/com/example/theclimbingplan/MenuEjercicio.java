package com.example.theclimbingplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuEjercicio extends AppCompatActivity {

    Button btnCrearEjercicio, btnModificarEjercicio, btnEliminarEjercicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ejercicio);
        btnCrearEjercicio = findViewById(R.id.btnCrearEjercicio);
        btnModificarEjercicio = findViewById(R.id.btnModificarEjercicio);
        btnEliminarEjercicio = findViewById(R.id.btnEliminarEjercicio);

        btnCrearEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(MenuEjercicio.this, FormularioEjercicio.class);
                intencion.putExtra("accion", "crear");
                startActivity(intencion);
            }
        });

        btnModificarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(MenuEjercicio.this, FormularioEjercicio.class);
                intencion.putExtra("accion", "modificar");
                startActivity(intencion);
            }
        });

        btnEliminarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(MenuEjercicio.this, FormularioEjercicio.class);
                intencion.putExtra("accion", "eliminar");
                startActivity(intencion);
            }
        });
    }


}