package com.example.theclimbingplan.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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

public class RealizarEntrenamiento extends AppCompatActivity {

    Spinner spinnerCategoria, spinnerSesion;
    BaseDatos baseDatos;
    List<SerieSesion> listaSerieSesion = new ArrayList<>();
    List<Serie> listaSeries = new ArrayList<>();
    LinearLayout linearSeleccion, linearLayoutRealizar, linearPlayPause;
    TextView tvNombreSerieActiva, tvNombreSesionElegida, tvEjerSerie, tvCronometro, tvCiclosRestantes, tvInfoRepes;
    Button btnEmpezar, btnPlay, btnReset, btnNextSerie, btnNextCiclo;

    boolean esDescanso = false;
    int contadorCiclos = 0;
    int contadorSeries = 0;
    Serie serieActual;
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
        linearPlayPause = findViewById(R.id.linearPlayPause);
        tvNombreSerieActiva = findViewById(R.id.tvNombreSerieActiva);
        tvNombreSesionElegida = findViewById(R.id.tvNombreSesionElegida);
        tvEjerSerie = findViewById(R.id.tvEjerSerie);
        tvInfoRepes = findViewById(R.id.tvInfoRepes);
        btnEmpezar = findViewById(R.id.btnEmpezarEntreno);
        tvCronometro = findViewById(R.id.chronometer);
        tvCiclosRestantes = findViewById(R.id.tvCiclosRestantes);
        btnPlay = findViewById(R.id.btnPlay);
        btnReset = findViewById(R.id.btnReset);
        btnNextSerie = findViewById(R.id.btnNextSerie);
        btnNextCiclo = findViewById(R.id.btnNextCiclo);
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
                empezarSesion(sesionElegida);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if cuenta null??
                iniciarCrono(serieActual.getDuracion());
                btnPlay.setEnabled(false);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausarCrono();
                btnPlay.setEnabled(true);
            }
        });

        btnNextSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSerie();
            }
        });

        btnNextCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCiclo();
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

    public long obtenerMilis(float tiempo){
        int minutos = (int) tiempo;
        int segundos = Math.round((tiempo - minutos) * 60);
        return (minutos * 60 + segundos) * 1000;
    }

    public void mostrarTiempo(long l){
        long tiempolong = l / 1000;
        int minu = (int) tiempolong / 60;
        long seg = tiempolong % 60;
        String minutos_mostrar = String.format("%02d", minu);
        String segundos_mostrar = String.format("%02d", seg);
        tvCronometro.setText(minutos_mostrar + " : " + segundos_mostrar);
        if(esDescanso){
            tvCronometro.setTextColor(Color.RED);
        }
        else{
            tvCronometro.setTextColor(Color.GREEN);
        }
    }
    public void iniciarCrono(float tiempo){
        long valor = obtenerMilis(tiempo);
        cuenta = new CountDownTimer(valor, 1000) {
            @Override
            public void onTick(long l) {
                mostrarTiempo(l);
            }
                @Override
                public void onFinish() {
                    tvCronometro.setText("00 : 00");
                    if(!esDescanso){
                        esDescanso = true;
                        playDescanso();
                    }
                    else{
                        esDescanso = false;
                        nextCiclo();
                    }

                }
            }.start();
    }
    public void pausarCrono() {
        if (cuenta != null) {
            tvCronometro.setText("00 : 00");
            cuenta.cancel();
            cuenta = null;
        }
    }


    //MËTODOS INICIO SESION ENTRENAMIENTO
    public void empezarSesion(Sesion sesionElegida){
        linearSeleccion.setVisibility(View.INVISIBLE);
        linearLayoutRealizar.setVisibility(View.VISIBLE);
        tvNombreSesionElegida.setText(tvNombreSesionElegida.getText() + ": " + sesionElegida.getNombre());
        setSerie();
    }

    public void setSerie(){
        serieActual = listaSeries.get(contadorSeries);
        tvNombreSerieActiva.setText("Serie: " + serieActual.getNombre());
        String ejercicio = baseDatos.daoEjercicio().consultarEjercicioPorID(serieActual.getIdEjercicio()).getNombre();
        tvEjerSerie.setText("Ejercicio: " + ejercicio);
        valortvCronometro();
        valortvCiclos();
    }

    public void valortvCronometro(){
        if(serieActual.getDuracion() != 0.0){
            linearPlayPause.setVisibility(View.VISIBLE);
            tvInfoRepes.setText("Tiempo:");
            long duracion = obtenerMilis(serieActual.getDuracion());
            mostrarTiempo(duracion);
        }
        if(serieActual.getRepeticiones() != 0){
            linearPlayPause.setVisibility(View.INVISIBLE);
            tvInfoRepes.setText("Repeticiones:");
            tvCronometro.setText(String.format("%d", serieActual.getRepeticiones()));
        }
    }

    public void valortvCiclos(){
        tvCiclosRestantes.setText(String.format("%d", serieActual.getCiclos() - contadorCiclos));
    }

    public void nextSerie(){
        contadorSeries++;
        if(contadorSeries >= listaSeries.size()){
            Toast.makeText(RealizarEntrenamiento.this, "Entrenamiento finalizado!", Toast.LENGTH_LONG).show();
            irHome();
            //Guardar en histórico
        }
        else{
            serieActual = listaSeries.get(contadorSeries);
            contadorCiclos = 0;
            pausarCrono();
            setSerie();
        }
    }

    public void nextCiclo(){
        if(!esDescanso) {
            btnPlay.setEnabled(true);
            contadorCiclos++;
            if (contadorCiclos > serieActual.getCiclos()) {
                contadorSeries++;
                if (contadorSeries >= listaSeries.size()) {
                    Toast.makeText(RealizarEntrenamiento.this, "Entrenamiento finalizado!", Toast.LENGTH_LONG).show();
                    irHome();
                    //Guardar en histórico
                } else {
                    serieActual = listaSeries.get(contadorSeries);
                    pausarCrono();
                    setSerie();
                }
            } else {
                pausarCrono();
                setSerie();
            }
        }
        else{
            playDescanso();
        }
    }

    public void playDescanso(){
        linearPlayPause.setVisibility(View.INVISIBLE);
        tvInfoRepes.setText("Descanso:");
        iniciarCrono(serieActual.getDescansoCiclo());
    }

    public void irHome(){
        Intent intencion = new Intent(RealizarEntrenamiento.this, HomeActivity.class);
        startActivity(intencion);
    }

}