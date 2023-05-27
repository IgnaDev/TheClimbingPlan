package com.example.theclimbingplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valorCorrecto(String.valueOf(etNombreCategoria.getText()))
                        && valorCorrecto(String.valueOf(etDescripcionCategoria.getText()))){
                    //comprobar si nombre existe
                    insertarCategoria(String.valueOf(etNombreCategoria.getText()),String.valueOf(etDescripcionCategoria.getText()));
                    //toast exito
                }
                else{
                    //toast fracaso
                }
            }
        });
    }

    public boolean valorCorrecto(String valor){
        return valor.isEmpty();
    }

    public void insertarCategoria(String nombre, String descripcion){
        baseDatos.daoCategoria().insertarCategoria(new Categoria(nombre, descripcion));
    }
}