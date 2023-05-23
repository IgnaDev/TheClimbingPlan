package com.example.theclimbingplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FormularioEntrenamiento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_entrenamiento);
    }

    public void irEjercicio(View view){
        Intent intent = new Intent(this, MenuEjercicio.class);
        startActivity(intent);

    }
}