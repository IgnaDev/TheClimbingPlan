package com.example.theclimbingplan.Activities;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theclimbingplan.DataBase.BaseDatos;
import com.example.theclimbingplan.Entities.Categoria;
import com.example.theclimbingplan.R;
import com.example.theclimbingplan.Entities.Serie;
import com.example.theclimbingplan.Entities.SerieSesion;
import com.example.theclimbingplan.Entities.Sesion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FormularioEntrenamiento extends AppCompatActivity {

    EditText etNombreEntrenamiento, etDescripcionEntrenamiento;
    Spinner spinnerCategoria;
    BaseDatos baseDatos;
    boolean vienenDatos;
    LinearLayout linearDatos;
    FloatingActionButton btnCrearCategoria;
    Button btnAddSeries, btnVolverFormularioEntr, btnAceptarFormuEntre, btnGuardar;

    TextView tvPrueba, tvInfo, tvInfo2;
    //private static final int CREATE_SERIE_REQUEST = 1;
    private List<Serie> seriesList = new ArrayList<>();
    ArrayList<String> listaNombresSeries = new ArrayList<>();

    String nombreEntrenamiento;
    String descripcionEntrenamiento;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_entrenamiento);
        etNombreEntrenamiento = findViewById(R.id.etNombreEntrenamiento);
        etDescripcionEntrenamiento = findViewById(R.id.etDescripcionEntrenamiento);
        btnAddSeries = findViewById(R.id.btnAddSeries);
        btnAceptarFormuEntre = findViewById(R.id.btnAceptarFormuEntre);
        btnVolverFormularioEntr = findViewById(R.id.btnVolverFormularioEntr);
        btnGuardar = findViewById(R.id.btnNombrarSesion);
        tvPrueba = findViewById(R.id.tvPrueba);
        tvInfo = findViewById(R.id.tvInfo);
        tvInfo2 = findViewById(R.id.tvInfo2);
        linearDatos = findViewById(R.id.linearDatos);
        spinnerCategoria = findViewById(R.id.spinnerCategoria1);
        btnCrearCategoria = findViewById(R.id.btnCrearCategoria1);

        setTitle("Formulario Entrenamiento");
        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();

        nombreEntrenamiento = "";
        descripcionEntrenamiento="";
        vienenDatos = false;

        getBundleSerie();
        if(vienenDatos){
            nombrarSesion();
            etNombreEntrenamiento.setText(nombreEntrenamiento);
            etDescripcionEntrenamiento.setText(descripcionEntrenamiento);
            btnAceptarFormuEntre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    crearSesion(seriesList);
                }
            });
            btnVolverFormularioEntr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    irMenuEntrenamiento();
                }
            });
            btnCrearCategoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    irCrearCategoria();
                }
            });
        }
        else {
            pruebatv(listaNombresSeries);

            btnAddSeries.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    irCrearSerie(listaNombresSeries);
                }
            });
            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listaNombresSeries.size() > 0) {
                        nombrarSesion();
                    } else {
                        Toast.makeText(FormularioEntrenamiento.this, "Debe añadior al menos una serie al entrenamiento", Toast.LENGTH_LONG).show();
                    }
                }
            });
            btnCrearCategoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    irCrearCategoria();
                }
            });
            btnAceptarFormuEntre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    crearSesion(seriesList);
                }
            });
            btnVolverFormularioEntr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    irMenuEntrenamiento();
                }
            });

        }
    }

    public void adapterSpinnerCategoria(){
        List<String> listaCategorias;
        listaCategorias = baseDatos.daoCategoria().consultarNombresCategorias();
        if(listaCategorias.size() > 0){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaCategorias);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategoria.setAdapter(adapter);
        }

    }


    public void pruebatv(ArrayList<String> seriesList){
        String cadena = "";
        for(String s : seriesList){
            cadena = cadena + s + "\n";
            tvPrueba.setText(cadena);
        }
    }

    public void getBundleSerie() {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                ArrayList<String> listaSeries =  bundle.getStringArrayList("listaSeries");
                nombreEntrenamiento = bundle.getString("nombre");
                descripcionEntrenamiento = bundle.getString("descripcion");
                if (nombreEntrenamiento != null){
                    vienenDatos = true;
                }
                if(listaSeries.size() > 0){
                    listaNombresSeries.addAll(listaSeries);
                }
            }
        }
    public void irCrearSerie(ArrayList<String> listaNombresSeries){

        Intent intent = new Intent(this, CrearSerie.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("listaSeries", listaNombresSeries);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void irCrearCategoria(){
        Intent intent = new Intent(this, CrearCategoria.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("listaSeries", listaNombresSeries);
        bundle.putString("nombre", String.valueOf(etNombreEntrenamiento.getText()));
        bundle.putString("descripcion", String.valueOf(etDescripcionEntrenamiento.getText()));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void irMenuEntrenamiento(){
        Intent intent = new Intent(this, MenuEntrenamiento.class);
        startActivity(intent);
    }


    private List<String> listaNombresSesiones(){
        return baseDatos.daoSesion().consultarNombreSesiones();
    }

    private boolean nombreExiste(String nombre, List<String> listaNombresSesiones){
        boolean existe = false;
        for (String s : listaNombresSesiones){
            if (nombre.equals(s)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    public void crearSesion(List<Serie> listaSeries){
        String nombre = etNombreEntrenamiento.getText().toString();
        String descripcion = etDescripcionEntrenamiento.getText().toString();
        String nombrecategoria = (String) spinnerCategoria.getSelectedItem();
        if(nombre.isEmpty() || descripcion.isEmpty() || nombrecategoria.isEmpty()){
            Toast.makeText(FormularioEntrenamiento.this, "Debe rellenar todos los campos para continuar", Toast.LENGTH_LONG).show();
        }
        else{
            List<String> listaNombres = listaNombresSesiones();
            if(nombreExiste(nombre, listaNombres)){
                Toast.makeText(FormularioEntrenamiento.this, "Ya existe un entrenamiento con ese nombre", Toast.LENGTH_LONG).show();
            }
            else{
                if(listaNombresSeries.size() <= 0){
                    Toast.makeText(FormularioEntrenamiento.this, "Debe añadior al menos una serie al entrenamiento", Toast.LENGTH_LONG).show();
                }
                else {
                    Categoria categoria = baseDatos.daoCategoria().consultarCategoriasPorNombre(nombrecategoria);
                    Sesion sesion = new Sesion(nombre, descripcion, categoria.getIdCategoria());
                    baseDatos.daoSesion().insertarSesion(sesion);
                    int idSesion = baseDatos.daoSesion().consultaridPorNombre(sesion.getNombre());
                    for(String n:listaNombresSeries){
                        Serie serie = baseDatos.daoSerie().consultarSeriePorNombre(n);
                        listaSeries.add(serie);
                    }
                    for (Serie s : listaSeries) {
                        //baseDatos.daoSerie().insertarSerie(s);
                        SerieSesion ss = new SerieSesion(s.getNombre(), idSesion);
                        baseDatos.daoSerieSesion().insertarSerieSesion(ss);
                    }
                    irMenuEntrenamiento();
                }
            }
        }
    }

    public void nombrarSesion(){
        //invisibilizar
        tvInfo2.setVisibility(View.INVISIBLE);
        tvInfo.setVisibility(View.INVISIBLE);
        tvPrueba.setVisibility(View.INVISIBLE);
        btnGuardar.setVisibility(View.INVISIBLE);
        btnAddSeries.setVisibility(View.INVISIBLE);
        //reducir
        tvPrueba.setHeight(0);
        tvInfo.setHeight(0);
        tvInfo2.setHeight(0);
        btnGuardar.setHeight(0);
        btnAddSeries.setHeight(0);
        //visibilizar
        linearDatos.setVisibility(View.VISIBLE);
        adapterSpinnerCategoria();
    }
}