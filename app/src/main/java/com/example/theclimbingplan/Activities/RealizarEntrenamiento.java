package com.example.theclimbingplan.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theclimbingplan.DataBase.BaseDatos;
import com.example.theclimbingplan.Entities.Categoria;
import com.example.theclimbingplan.Entities.Serie;
import com.example.theclimbingplan.Entities.SerieSesion;
import com.example.theclimbingplan.Entities.Sesion;
import com.example.theclimbingplan.R;

import java.util.ArrayList;
import java.util.List;

public class RealizarEntrenamiento extends AppCompatActivity implements View.OnClickListener{

    Spinner spinnerCategoria, spinnerSesion;
    BaseDatos baseDatos;
    List<SerieSesion> listaSerieSesion = new ArrayList<>();
    List<Serie> listaSeries = new ArrayList<>();
    LinearLayout linearSeleccion, linearLayoutRealizar;
    TextView tvNombreSerieActiva, tvNombreSesionElegida, tvEjerSerie, tvCronometro;
    Button btnEmpezar, btnPlay, btnPause, btnReset;

    int contadorCiclos = 0;
    int contadorSeries = 0;
    private CountDownTimer cuenta;
    private long tiempoRestante = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_entrenamiento);
        spinnerSesion = findViewById(R.id.spinnerSesion);
        spinnerCategoria = findViewById(R.id.spinnerCategoriaSesion);
        linearSeleccion = findViewById(R.id.linearSeleccion);
        linearLayoutRealizar = findViewById(R.id.linearLayoutRealizar);
        tvNombreSerieActiva = findViewById(R.id.tvNombreSerieActiva);
        tvNombreSesionElegida = findViewById(R.id.tvNombreSesionElegida);
        tvEjerSerie = findViewById(R.id.tvEjerSerie);
        btnEmpezar = findViewById(R.id.btnEmpezarEntreno);
        tvCronometro = findViewById(R.id.chronometer);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnReset = findViewById(R.id.btnReset);

        /*
        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(SystemClock.elapsedRealtime() - crono.getBase() >= 60000){
                    crono.setBase(SystemClock.elapsedRealtime());
                }
            }
        });

         */

        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();

        setTitle("Realizar Entenamiento");
        adapterSpinnerCategoria();
        adapterSpinnerSesion("");

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nombreCategoria = spinnerCategoria.getSelectedItem().toString();
                adapterSpinnerSesion(nombreCategoria);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                adapterSpinnerSesion("");
            }
        });

        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sesion sesionElegida = baseDatos.daoSesion().consultarSesionesPorNombre(spinnerSesion.getSelectedItem().toString());
                listaSerieSesion = baseDatos.daoSerieSesion().obtenerSeriesPorSesion(sesionElegida.getIdSesion());
                for (SerieSesion ss: listaSerieSesion){
                    Serie serie = baseDatos.daoSerie().consultarSeriePorNombre(ss.getNombreSerie());
                    listaSeries.add(serie);
                }
                empezarSesion(sesionElegida, listaSeries);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarCrono((float) 10.20);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausarCrono();
            }
        });

    }
    //METODOS PARA ADAPTAR LOS SPINNER
    public void adapterSpinnerCategoria(){
        List<String> listaCategorias = new ArrayList<>();
        listaCategorias = baseDatos.daoCategoria().consultarNombresCategorias();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
    }
    public void adapterSpinnerSesion(String nombreCategoria){
        List<String> listaNombresSesiones = new ArrayList<>();
        List<Sesion> listaSesiones = new ArrayList<>();
        if(nombreCategoria.equals("")){
            listaNombresSesiones = baseDatos.daoSesion().consultarNombreSesiones();
        }
        else{
            Categoria categoria = baseDatos.daoCategoria().consultarCategoriasPorNombre(nombreCategoria);
            listaSesiones = baseDatos.daoSesion().consultarSesionesPorCategoria(categoria.getIdCategoria());
            for(Sesion s : listaSesiones){
                listaNombresSesiones.add(s.getNombre());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaNombresSesiones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSesion.setAdapter(adapter);
    }

    //METEDOS CRONOMETRO
    public void iniciarCrono(float tiempo){
        int minutos = (int) tiempo;
        int segundos = (int) (tiempo - minutos) * 60;
        long valor;
        valor = (minutos * 60 + segundos) * 1000; // Si no, calculamos el tiempo inicial

        cuenta = new CountDownTimer(valor, 1000) {
            @Override
            public void onTick(long l) {
                long tiempolong = l / 1000;
                int minu = (int) tiempolong / 60;
                long seg = tiempolong % 60;
                String minutos_mostrar = String.format("%d", minu);
                String segundos_mostrar = String.format("%02d", seg);
                tvCronometro.setText(minutos_mostrar + " : " + segundos_mostrar);
                }
                @Override
                public void onFinish() {
                    Toast.makeText(RealizarEntrenamiento.this, "Serie creada con éxito", Toast.LENGTH_LONG).show();
                }
            }.start();


    }

    public void pausarCrono() {
        if (cuenta != null) {
            cuenta.cancel();
            cuenta = null;
        }
    }
    /*


    public void startCrono(){
        if(!running){
            crono.setBase(SystemClock.elapsedRealtime()-pauseoffset);
            crono.start();
            running = true;
        }
    }

    public void pauseCrono(View v){
        if(running){
            crono.stop();
            pauseoffset = SystemClock.elapsedRealtime() - crono.getBase();
            running = false;
        }
    }

    public void resetCrono(View v){
        crono.setBase(SystemClock.elapsedRealtime());
        pauseoffset = 0;
    }


     */


    public void empezarSesion(Sesion sesionElegida, List<Serie> listaSeries){
        linearSeleccion.setVisibility(View.INVISIBLE);
        linearLayoutRealizar.setVisibility(View.VISIBLE);
        tvNombreSesionElegida.setText(tvNombreSesionElegida.getText() + ": " + sesionElegida.getNombre());
        infoSerie(listaSeries);
    }

    public void infoSerie(List<Serie> listaSeries){
        Serie serie = listaSeries.get(contadorSeries);
        tvNombreSerieActiva.setText(tvNombreSerieActiva.getText() + ": " + serie.getNombre());
        String ejercicio = baseDatos.daoEjercicio().consultarEjercicioPorID(serie.getIdEjercicio()).getNombre();
        tvEjerSerie.setText(tvEjerSerie.getText() + ": " + ejercicio);
    }
    public void seriesInit(List<Serie> listaSeries){

    }

    @Override
    public void onClick(View v) {

    }
}