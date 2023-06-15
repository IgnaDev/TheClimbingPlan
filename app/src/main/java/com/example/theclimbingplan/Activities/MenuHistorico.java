package com.example.theclimbingplan.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.theclimbingplan.DataBase.BaseDatos;
import com.example.theclimbingplan.Entities.Categoria;
import com.example.theclimbingplan.Entities.Historico;
import com.example.theclimbingplan.Entities.Sesion;
import com.example.theclimbingplan.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuHistorico extends AppCompatActivity {
    BaseDatos baseDatos;
    TextView tv, tv2;
    Spinner spinnerCategoria, spinnerSesion;

    List<Date> listaFechas = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_historico);
        tv = findViewById(R.id.tvCategoriaHist);
        tv2 = findViewById(R.id.textView5);
        spinnerCategoria = findViewById(R.id.spinnerCategoriaSesionHist);
        spinnerSesion = findViewById(R.id.spinnerSesionHist);

        baseDatos = Room.databaseBuilder(
                getApplicationContext(),
                BaseDatos.class,
                "@DBPruebas"
        ).allowMainThreadQueries().build();
        adapterSpinnerCategoria();
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

        spinnerSesion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sesion sesion = baseDatos.daoSesion().consultarSesionesPorNombre(spinnerSesion.getSelectedItem().toString());
                listaFechas = baseDatos.daoHistorico().consultarfechasPorSesion(sesion.getIdSesion());
                String cadena = "";
                for(Date d:listaFechas){
                    cadena = cadena + d.toString() + "\n";
                    tv2.setText( cadena);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Date today = new Date();
        //baseDatos.daoHistorico().insertarHistorico(new Historico(1, today, 5, "ok"));

    }


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
}