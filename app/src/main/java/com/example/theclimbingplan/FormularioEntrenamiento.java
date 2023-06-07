package com.example.theclimbingplan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FormularioEntrenamiento extends AppCompatActivity {
    LinearLayout seriesLayout;
    EditText etNombreEntrenamiento, etDescripcionEntrenamiento;
    Spinner spinnerCategoría;
    BaseDatos baseDatos;
    LinearLayout serieLayout;
    FloatingActionButton btnCrearCategoria, btnAddSerie;
    Button btnAddSeries, btnVolverFormularioEntr, btnAceptarFormuEntre;
    //private static final int CREATE_SERIE_REQUEST = 1;
    private List<Serie> seriesList = new ArrayList<>();
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
        serieLayout = findViewById(R.id.seriesLayout);
        btnAddSeries = findViewById(R.id.btnAddSeries);
        seriesLayout = findViewById(R.id.seriesLayout);
        setTitle("Formulario Entrenamiento");
        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();
//TODO botón eliminar serie
        //TODO añadir serie existente
        //TODO crear tabla intermedia serie sesion
        getBundleSerie(seriesList);
        //CATEGORIA
        adapterSpinnerCategoria();

        btnCrearCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irCrearCategoria();
            }
        });

        btnAddSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarAddSerie();
            }
        });

        //SERIES
        btnAddSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irCrearSerie(getidSesionActual(etNombreEntrenamiento.getText().toString()));
            }
        });

    }

    public int getidSesionActual(String nombre){
        return baseDatos.daoSesion().consultarSesionesPorNombre(nombre).idSesion;
    }
    public void adapterSpinnerCategoria(){
        List<String> listaCategorias = new ArrayList<>();
        listaCategorias = baseDatos.daoCategoria().consultarNombresCategorias();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoría.setAdapter(adapter);
    }

    public List<SerieSesion> getSeriesSesion(int idSesion){
        return baseDatos.daoSerieSesion().obtenerSeriesPorSesion(idSesion);
    }

    public void irCrearSerie(int idSesion){
        Intent intent = new Intent(this, CrearSerie.class);
        Bundle bundle = new Bundle();
        bundle.putString("idSesion", String.valueOf(idSesion));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void getBundleSerie(List<Serie> seriesList) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                Serie serie = (Serie) bundle.getSerializable("serie");
                seriesList.add(serie);
                agregarSeries(seriesList);
            }
        }

    public void irCrearCategoria(){
        Intent intent = new Intent(this, CrearCategoria.class);
        startActivity(intent);
    }

    private void agregarSeries(List<Serie> listaSeries){
        for(Serie e : listaSeries){
            // Inflar la vista del Serie desde un archivo XML de diseño
            View serieView = getLayoutInflater().inflate(R.layout.item_serie, null);

            // Configurar la vista de la Serie con los datos correspondientes
            TextView tvNombreSerie = serieView.findViewById(R.id.tvNombreSerie);
            tvNombreSerie.setText(e.nombre);

            // Obtener referencia al botón flotante "borrar"
            FloatingActionButton btnDeleteSerie = serieView.findViewById(R.id.btnDeleteSerie);
            btnDeleteSerie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listaSeries.remove(e);
                    recreate();
                }
            });

            // Añadir la vista de la ejercicio al LinearLayout
            serieLayout.addView(serieView);
        }
    }

    private List<String> listaNombresSesiones(){
        return baseDatos.daoSesion().consultarNombreSesiones();
    }

    private boolean nombreExiste(String nombre, List<String> listaNombresSesiones){
        boolean existe = false;
        for (String s : listaNombresSesiones){
            if(nombre.equals(s)){
                existe = true;
            }
        }
        return existe;
    }

    public void comprobarAddSerie(){
        String nombre = etNombreEntrenamiento.getText().toString();
        String descripcion = etDescripcionEntrenamiento.getText().toString();
        Categoria categoria = (Categoria) spinnerCategoría.getSelectedItem();
        if(nombre.isEmpty() || descripcion.isEmpty() || categoria == null){
            Toast.makeText(FormularioEntrenamiento.this, "Debe rellenar todos los campos para continuar", Toast.LENGTH_LONG).show();
        }
        else{
            List<String> listaNombres = listaNombresSesiones();
            if(nombreExiste(nombre, listaNombres)){
                Toast.makeText(FormularioEntrenamiento.this, "Ya existe un entrenamiento con ese nombre", Toast.LENGTH_LONG).show();
            }
            else{
                Sesion sesion = new Sesion(nombre, descripcion, categoria.idCategoria);
                baseDatos.daoSesion().insertarSesion(sesion);
                irCrearSerie(sesion.idSesion);
                btnAddSerie.setVisibility(View.VISIBLE);
            }
        }
    }

    private List<Serie> listaSeriesPorSesion(int idSesion){
        List<SerieSesion> listaSS = baseDatos.daoSerieSesion().obtenerSeriesPorSesion(idSesion);
        List<Serie> listaSeries = new ArrayList<>();
        for(SerieSesion ss : listaSS){
            Serie s = baseDatos.daoSerie().consultarSeriePorNombre(ss.nombreSerie);
            listaSeries.add(s);
        }
        return listaSeries;
    }

    private void agregarSeriess(List<Serie> listaSeries){
        for(Serie e : listaSeries){
            // Inflar la vista del ejercicio desde un archivo XML de diseño
            View serieView = getLayoutInflater().inflate(R.layout.item_sesion, null);

            // Configurar la vista de la ejercicio con los datos correspondientes
            TextView tvNombress = serieView.findViewById(R.id.tvNombress);
            tvNombress.setText(e.nombre);

            // Obtener referencia al botón flotante "agregar"
            FloatingActionButton btnAddEjer = serieView.findViewById(R.id.btnAddss);
            btnAddEjer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SerieSesion ss = new SerieSesion(e.nombre, getidSesionActual(etNombreEntrenamiento.getText().toString()));
                    eliminarSerieSesion(ss);
                    agregarSeriess(listaSeries);
                }
            });

            // Añadir la vista de la ejercicio al LinearLayout
            seriesLayout.addView(serieView);
        }
    }

    public void eliminarSerieSesion(SerieSesion serieSesion){
        baseDatos.daoSerieSesion().eliminarSerieSesion(serieSesion);
    }
}