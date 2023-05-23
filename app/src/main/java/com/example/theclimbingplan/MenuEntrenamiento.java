package com.example.theclimbingplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuEntrenamiento extends AppCompatActivity {

    Button btnRealizarEntrenamiento, btnCrearEntrenamiento, btnModificarEntrenamiento, btnEliminarEntrenamiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_entrenamiento);
        btnRealizarEntrenamiento = findViewById(R.id.btnRealizarEntrenamiento);
        btnCrearEntrenamiento = findViewById(R.id.btnCrearSesion);
        btnModificarEntrenamiento = findViewById(R.id.btnModificarEntrenamiento);
        btnEliminarEntrenamiento = findViewById(R.id.btnEliminarEntrenamiento);

        btnRealizarEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(MenuEntrenamiento.this, RealizarEntrenamiento.class);
                startActivity(intencion);
            }
        });

        btnCrearEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(MenuEntrenamiento.this, FormularioEntrenamiento.class);
                intencion.putExtra("accion", "crear");
                startActivity(intencion);
            }
        });

        btnModificarEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(MenuEntrenamiento.this, FormularioEntrenamiento.class);
                intencion.putExtra("accion", "modificar");
                startActivity(intencion);
            }
        });

        btnEliminarEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(MenuEntrenamiento.this, FormularioEntrenamiento.class);
                intencion.putExtra("accion", "eliminar");
                startActivity(intencion);
            }
        });
    }
}