package com.example.theclimbingplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnMenuSesion, btnMenuHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMenuSesion = findViewById(R.id.btnEntrenoPrincipal);
        btnMenuHistorico = findViewById(R.id.btnHistoricoPrincipal);


    }



    public void irSesion(View view){
        Intent intent = new Intent(this, MenuEntrenamiento.class);
        startActivity(intent);
    }

    public void irHistorico(View view){
        Intent intent = new Intent(this, MenuHistorico.class);
        startActivity(intent);
    }
}