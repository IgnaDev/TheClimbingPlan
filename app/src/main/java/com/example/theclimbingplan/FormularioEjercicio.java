package com.example.theclimbingplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FormularioEjercicio extends AppCompatActivity {

    EditText etNombre, etDescripcion;
    Button btnAceptar, btnCancelar;
    AutoCompleteTextView autoCompleteTextView;
    BaseDatos baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_ejercicio);
        etNombre = findViewById(R.id.etNombreEjer);
        etDescripcion = findViewById(R.id.etDescripcionEjer);
        btnAceptar = findViewById(R.id.btnAceptarFormEjer);
        btnCancelar = findViewById(R.id.btnCancelarFormEjer);
        autoCompleteTextView = findViewById(R.id.autoCompGrupo);
        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();
        setTitle("Formulario Ejercicio");
        List<String> listaGrupos = nombresGrupos();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listaGrupos);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = adapter.getItem(position);
                // Acción al seleccionar un valor existente de la lista
            }
        });


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = String.valueOf(etNombre.getText());
                String descripcion = String.valueOf(etDescripcion.getText());
                String grupo = String.valueOf(autoCompleteTextView.getText());
                if(grupoExiste(grupo)){
                    int idGrupo = buscarIDgrupo(grupo);
                }
                else{
                    crearGrupo(grupo, "");
                }
                int idGrupo = buscarIDgrupo(grupo);

                if(nombre.isEmpty() || descripcion.isEmpty() || grupo.isEmpty()){
                    Toast.makeText(FormularioEjercicio.this, "Debe rellenar los campos para continuar", Toast.LENGTH_LONG).show();
                }
                else{
                    List<String> listaNombres = new ArrayList<>();
                    listaNombres = consultarNombres();
                    //comprobar si nombre existe
                    if(nombreExiste(nombre, listaNombres)){
                        //toast nombre ya existe
                        Toast.makeText(FormularioEjercicio.this, "Ya existe un ejercicio con este nombre", Toast.LENGTH_LONG).show();
                    }
                    else{
                        insertarEjercicio(nombre, descripcion, idGrupo);
                        //toast exito
                        Toast.makeText(FormularioEjercicio.this, "Ejercicio creado", Toast.LENGTH_LONG).show();
                        irCrearSerie();
                    }
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irCrearSerie();
            }
        });
    }

    public void irCrearSerie(){
        Intent intencion = new Intent(FormularioEjercicio.this, CrearSerie.class);
        startActivity(intencion);
    }

    public void insertarEjercicio(String nombre, String descripcion, int idGrupo){
        baseDatos.daoEjercicio().insertarEjercicio(new Ejercicio(nombre, descripcion, idGrupo));
    }

    public List<String> nombresGrupos(){
        return baseDatos.daoGrupo().consultarNombresGrupos();
    }

    public int buscarIDgrupo(String grupo){
        return  baseDatos.daoGrupo().consultarGruposPorNombre(grupo).idGrupo;
    }

    public List<String> consultarNombres(){
        return baseDatos.daoGrupo().consultarNombresGrupos();
    }

    public boolean nombreExiste(String nombre, List<String> listaNombres)
    {
        boolean nombreCorrecto = false;
        for (String s: listaNombres
        ) {
            if(s.equals(nombre)){
                nombreCorrecto = true;
            }
        }
        return nombreCorrecto;
    }

    public void crearGrupo(String nombre, String descripcion){
        baseDatos.daoGrupo().insertarGrupo(new Grupo(nombre, descripcion));
    }

    public boolean grupoExiste(String nombre){
        Grupo g = baseDatos.daoGrupo().consultarGruposPorNombre(nombre);
        if (g != null){
            return true;
        }
        return false;
    }
}