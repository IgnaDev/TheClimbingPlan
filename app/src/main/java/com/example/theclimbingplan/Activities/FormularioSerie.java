package com.example.theclimbingplan.Activities;

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

import com.example.theclimbingplan.DataBase.BaseDatos;
import com.example.theclimbingplan.R;
import com.example.theclimbingplan.Entities.Serie;

import java.util.ArrayList;
import java.util.List;

public class FormularioSerie extends AppCompatActivity {
    RadioGroup radioGroupSeleccion;
    TextView tvEjercicio;
    EditText editTextRepeticiones, editTextDuracion, etCiclos, etNombre, etDescanso;
    BaseDatos baseDatos;
    Button btnAceptar, btnCancelar;

    ArrayList<String> listaNombresSeries = new ArrayList<>();

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
        getBundleSerie();
        Bundle b=getIntent().getExtras();
        String nombre = b.getString("nombre");
        Intent intent = getIntent();
        //ArrayList<String> listaSeries =intent.getStringArrayListExtra("listaSeries");
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
                        if(nombreCorrecto(nombre)){
                            Serie serie = new Serie(idEjercicio,nombre,repeticiones,descanso, ciclos);
                            listaNombresSeries.add(serie.getNombre());
                            baseDatos.daoSerie().insertarSerie(serie);
                            Toast.makeText(FormularioSerie.this, "Serie creada con éxito", Toast.LENGTH_LONG).show();
                            irFormularioEntrenamiento(serie, listaNombresSeries);

                        }
                        else{
                            Toast.makeText(FormularioSerie.this, "Ya existe una serie con ese nombre", Toast.LENGTH_LONG).show();
                        }
                        }
                }
                if(radioGroupSeleccion.getCheckedRadioButtonId() == R.id.radioDuracion){
                    if(nombre.isEmpty() || duracion == 9999 || ciclos == 9999 || descanso == 9999){
                        Toast.makeText(FormularioSerie.this, "Debe rellenar todos los campos para continuar", Toast.LENGTH_LONG).show();
                    }
                    else{
                        if(nombreCorrecto(nombre)){
                            Serie serie = new Serie(idEjercicio,nombre,duracion,descanso, ciclos);
                            listaNombresSeries.add(serie.getNombre());
                            baseDatos.daoSerie().insertarSerie(serie);
                            Toast.makeText(FormularioSerie.this, "Serie creada con éxito", Toast.LENGTH_LONG).show();
                            irFormularioEntrenamiento(serie, listaNombresSeries);
                        }
                        else{
                            Toast.makeText(FormularioSerie.this, "Ya existe una serie con ese nombre", Toast.LENGTH_LONG).show();
                        }
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
                irCrearSerie(listaNombresSeries);
            }
        });
    }

    public void getBundleSerie() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ArrayList<String> listaSeries =  bundle.getStringArrayList("listaSeries");
            if(listaSeries.size() > 0){
                for(String s: listaSeries){
                    listaNombresSeries.add(s);
                }
            }
        }
    }

    public void irCrearSerie(ArrayList<String> listaSeries){
        Intent intencion = new Intent(FormularioSerie.this, CrearSerie.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("listaSeries", listaSeries);
        intencion.putExtras(bundle);
        startActivity(intencion);
    }

    public void irFormularioEntrenamiento(Serie serie, ArrayList<String> listaSeries){
        Bundle bundle = new Bundle();
        bundle.putSerializable("serie", serie);
        bundle.putStringArrayList("listaSeries", listaSeries);
        Intent intencion = new Intent(FormularioSerie.this, FormularioEntrenamiento.class);
        intencion.putExtras(bundle);
        setResult(RESULT_OK, intencion);
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

    public boolean nombreCorrecto(String nombre){
        List<String> nombresSeries = baseDatos.daoSerie().consultarNombreSeries();
        boolean correcto = true;
        for(String s : nombresSeries){
            if(nombre.equals(s)){
                correcto =  false;
            }
        }
        return correcto;
    }


}