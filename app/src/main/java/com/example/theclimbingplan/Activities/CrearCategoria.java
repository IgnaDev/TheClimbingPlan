package com.example.theclimbingplan.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.theclimbingplan.DataBase.BaseDatos;
import com.example.theclimbingplan.Entities.Categoria;
import com.example.theclimbingplan.R;

import java.util.ArrayList;
import java.util.List;

public class CrearCategoria extends AppCompatActivity {

    EditText etNombreCategoria, etDescripcionCategoria;
    Button btnAceptar, btnCancelar;
    BaseDatos baseDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_categoria);
        etDescripcionCategoria = findViewById(R.id.etDescripcionCategoria);
        etNombreCategoria = findViewById(R.id.etNombreCategoria);
        btnAceptar = findViewById(R.id.btnAceptarCrearCategoria);
        btnCancelar = findViewById(R.id.btnVolverCrearCategoria);
        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();
        setTitle("Crear Categoria");
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = String.valueOf(etNombreCategoria.getText());
                String descripcion = String.valueOf(etDescripcionCategoria.getText());
                if(nombre.isEmpty()
                        || descripcion.isEmpty()){
                    //toast completar campos
                    Toast.makeText(CrearCategoria.this, "Debe rellenar ambos campos para continuar", Toast.LENGTH_LONG).show();
                }
                else{
                    List<String> listaNombres = new ArrayList<>();
                    listaNombres = consultarNombres();
                    //comprobar si nombre existe
                    if(nombreExiste(nombre, listaNombres)){
                        //toast nombre ya existe
                        Toast.makeText(CrearCategoria.this, "Categoría ya existente", Toast.LENGTH_LONG).show();
                    }
                    else{
                        insertarCategoria(String.valueOf(etNombreCategoria.getText()),String.valueOf(etDescripcionCategoria.getText()));
                        //toast exito
                        Toast.makeText(CrearCategoria.this, "Categoría creada", Toast.LENGTH_LONG).show();
                        irFormularioEntrenamiento();
                    }
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irFormularioEntrenamiento();
            }
        });
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

    public List<String> consultarNombres(){
        return baseDatos.daoCategoria().consultarNombresCategorias();
    }
    public void insertarCategoria(String nombre, String descripcion){
        baseDatos.daoCategoria().insertarCategoria(new Categoria(nombre, descripcion));
    }

    public void irFormularioEntrenamiento(){
        Intent intencion = new Intent(CrearCategoria.this, FormularioEntrenamiento.class);
        startActivity(intencion);
    }
}