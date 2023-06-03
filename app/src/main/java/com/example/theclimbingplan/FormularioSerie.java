package com.example.theclimbingplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FormularioSerie extends AppCompatActivity {
    RadioGroup radioGroupSeleccion;
    TextView tvEjercicio;
    EditText editTextRepeticiones, editTextDuracion, etCiclos, etNombre, etDescanso;
    BaseDatos baseDatos;
    Button btnAceptar, btnCancelar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_serie);
        tvEjercicio = findViewById(R.id.tvEjerSeleccionado);
        radioGroupSeleccion = findViewById(R.id.radioGroupSeleccion);
        editTextRepeticiones = findViewById(R.id.editTextRepeticiones);
        editTextDuracion = findViewById(R.id.editTextDuracion);
        etCiclos = findViewById(R.id.editTextCiclos);
        etDescanso = findViewById(R.id.editTextDescansoCiclo);
        etNombre = findViewById(R.id.editTextNombreSerie);
        btnAceptar = findViewById(R.id.btnAceptarFormSerie);
        btnCancelar = findViewById(R.id.btnCancelarFormSerie);
        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();

        setTitle("Formulario Serie");

        Bundle b=getIntent().getExtras();
        String nombre = b.getString("nombre");
        tvEjercicio.setText(nombre);
        int idEjercicio = baseDatos.daoEjercicio().consultarIDEjercicioPorNombre(nombre);

        radioGroupSeleccion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioRepeticiones) {
                    editTextRepeticiones.setVisibility(View.VISIBLE);
                    editTextDuracion.setVisibility(View.INVISIBLE);
                }
                if (checkedId == R.id.radioDuracion) {
                    editTextRepeticiones.setVisibility(View.INVISIBLE);
                    editTextDuracion.setVisibility(View.VISIBLE);
                }
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = String.valueOf(etNombre.getText());
                int repeticiones = valorCampoInt(editTextRepeticiones);
                int ciclos = valorCampoInt(etCiclos);
                float duracion = valorCampofloat(editTextDuracion);
                float descanso = valorCampofloat(etDescanso);

                if(radioGroupSeleccion.getCheckedRadioButtonId() == R.id.radioRepeticiones){
                    if(nombre.isEmpty() || repeticiones == 9999 || ciclos == 9999 || descanso == 9999){
                        Toast.makeText(FormularioSerie.this, "Debe rellenar todos los campos para continuar", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Serie serie = new Serie(idEjercicio,nombre,repeticiones,descanso, ciclos);
                        baseDatos.daoSerie().insertarSerie(serie);
                        Toast.makeText(FormularioSerie.this, "Serie creada con éxito", Toast.LENGTH_LONG).show();
                        irCrearSerie();
                    }
                }
                if(radioGroupSeleccion.getCheckedRadioButtonId() == R.id.radioDuracion){
                    if(nombre.isEmpty() || duracion == 9999 || ciclos == 9999 || descanso == 9999){
                        Toast.makeText(FormularioSerie.this, "Debe rellenar todos los campos para continuar", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Serie serie = new Serie(idEjercicio,nombre,duracion,descanso, ciclos);
                        baseDatos.daoSerie().insertarSerie(serie);
                        Toast.makeText(FormularioSerie.this, "Serie creada con éxito", Toast.LENGTH_LONG).show();
                        irCrearSerie();
                    }
                }
                else{
                    Toast.makeText(FormularioSerie.this, "Debe elegir entre repeticiones o duración", Toast.LENGTH_LONG).show();
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
        Intent intencion = new Intent(FormularioSerie.this, CrearSerie.class);
        startActivity(intencion);
    }

    public int valorCampoInt(EditText editText){
        String text = editText.getText().toString();
        if (!text.isEmpty()) {
            return Integer.valueOf(text);
        } else {
            return 9999; // Valor por defecto de repeticiones si el campo está vacío
        }
    }

    public float valorCampofloat(EditText editText){
        String text = editText.getText().toString();
        if (!text.isEmpty()) {
            return Float.valueOf(text);
        } else {
            return 9999; // Valor por defecto de repeticiones si el campo está vacío
        }
    }


}