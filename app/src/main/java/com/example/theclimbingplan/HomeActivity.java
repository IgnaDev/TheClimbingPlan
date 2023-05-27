package com.example.theclimbingplan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

enum ProviderType{
    BASIC,
    GOOGLE
}
public class HomeActivity extends AppCompatActivity {

    Button btnLogOut, btnMenuEntenamiento, btnMenuHistorico;
    FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnMenuEntenamiento = findViewById(R.id.btnMenuEntrenamiento);
        btnMenuHistorico = findViewById(R.id.btnMenuHistorico);
        mAuth = FirebaseAuth.getInstance();

        setup();

    }
    private void setup(){
        setTitle("Inicio");
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(HomeActivity.this, AuthActivity.class));
            }
        });

        btnMenuEntenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irSesion();
            }
        });

        btnMenuHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irHistorico();
            }
        });
    }
    public void irSesion(){
        Intent intent = new Intent(this, MenuEntrenamiento.class);
        startActivity(intent);
    }

    public void irHistorico(){
        Intent intent = new Intent(this, MenuHistorico.class);
        startActivity(intent);
    }


}