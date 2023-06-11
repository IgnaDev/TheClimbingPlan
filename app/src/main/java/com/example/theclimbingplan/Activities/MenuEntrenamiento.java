package com.example.theclimbingplan.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.theclimbingplan.DataBase.BaseDatos;
import com.example.theclimbingplan.R;

public class MenuEntrenamiento extends AppCompatActivity {

    Button btnRealizarEntrenamiento, btnCrearEntrenamiento;
    BaseDatos baseDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_entrenamiento);
        btnRealizarEntrenamiento = findViewById(R.id.btnRealizarEntrenamiento);
        btnCrearEntrenamiento = findViewById(R.id.btnCrearSesion);
        setTitle("Entrenamiento");
        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();
        btnRealizarEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //baseDatos.daoSesion().insertarSesion(new Sesion("aasdd", "aadd", 2));
                irRealizarEntrenamiento();
            }
        });

        btnCrearEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irFormularioEntrenamiento();
            }
        });

    }

    public void irFormularioEntrenamiento(){
        Intent intencion = new Intent(MenuEntrenamiento.this, FormularioEntrenamiento.class);
        startActivity(intencion);
    }

    public void irRealizarEntrenamiento(){
        Intent intencion = new Intent(MenuEntrenamiento.this, RealizarEntrenamiento.class);
        startActivity(intencion);
    }


}