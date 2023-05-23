package com.example.theclimbingplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    //Button btnMenuSesion, btnMenuHistorico;
    Button btnSignUp, btnLogIn;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        //btnMenuSesion = findViewById(R.id.btnEntrenoPrincipal);
        //btnMenuHistorico = findViewById(R.id.btnHistoricoPrincipal);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
// Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //lanzando evento personalizado a google analytics
        Bundle b = new Bundle();
        b.putString("message", "Integración de Firebase Completa");
        mFirebaseAnalytics.logEvent("InitScreen", b);

        //setup

    }

    private void setup(){
        setTitle("Autenticación");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(etEmail.getText()) != "" && String.valueOf(etPassword.getText()) != ""){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword
                            (etEmail.getText().toString(), etPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        irSesion();
                                    } else {
                                        showAlert();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Error al autenticar Usuario");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
        //AlertDialog alertDialog = new AlertDialog();
    }


    public void irSesion(){
        Intent intent = new Intent(this, MenuEntrenamiento.class);
        startActivity(intent);
    }
/*
    public void irHistorico(View view){
        Intent intent = new Intent(this, MenuHistorico.class);
        startActivity(intent);
    }

 */
}