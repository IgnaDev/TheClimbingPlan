package com.example.theclimbingplan.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.theclimbingplan.DataBase.BaseDatos;
import com.example.theclimbingplan.Entities.Historico;
import com.example.theclimbingplan.Entities.Sesion;
import com.example.theclimbingplan.R;

import java.util.Date;

public class ConsultaHistorico extends AppCompatActivity {

    RatingBar starBar;
    EditText comentarios;

    BaseDatos baseDatos;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_historico);
        setTitle("Consultar Histórico");
        starBar = findViewById(R.id.ratingBar);
        comentarios=findViewById(R.id.etComentarios);
        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();


        Intent intent = getIntent();
        Sesion sesion = (Sesion) intent.getSerializableExtra("sesion");

        starBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int puntuacion = starBar.getNumStars();
                String coment = String.valueOf(comentarios.getText());
                Date today = new Date();
                Historico hist = new Historico(sesion.getIdSesion(), today, puntuacion, coment);
                baseDatos.daoHistorico().insertarHistorico(hist);
                Toast.makeText(ConsultaHistorico.this, "Sesión guardad en el historico", Toast.LENGTH_LONG).show();
                irHome();
            }
        });
         }

    public void irHome(){

        Intent intencion = new Intent(ConsultaHistorico.this, HomeActivity.class);
        startActivity(intencion);
    }
}