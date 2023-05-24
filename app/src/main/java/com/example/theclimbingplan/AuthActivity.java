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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    //Button btnMenuSesion, btnMenuHistorico;
    Button btnSignUp, btnLogIn;
    EditText etEmail, etPassword;
    FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();
// Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //lanzando evento personalizado a google analytics
        Bundle b = new Bundle();
        b.putString("message", "Integración de Firebase Completa");
        mFirebaseAnalytics.logEvent("InitScreen", b);

        //setup
        setup();
    }

    private void setup(){
        setTitle("Autenticación");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if(email.isEmpty() && password.isEmpty()){
                    Toast.makeText(AuthActivity.this, "Debe rellenar los campos mostrados", Toast.LENGTH_LONG).show();
                }
                else{
                    signUpUser(email, password);
                }
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if(email.isEmpty() && password.isEmpty()){
                    Toast.makeText(AuthActivity.this, "Debe rellenar los campos mostrados", Toast.LENGTH_LONG).show();
                }
                else{
                    loginUser(email, password);
                }
            }
        });
    }
    private void signUpUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(AuthActivity.this, HomeActivity.class));
                    Toast.makeText(AuthActivity.this, "Usuario registrado con éxito", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AuthActivity.this, "Error de registro", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AuthActivity.this, "Error al registrar usuario", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(AuthActivity.this, HomeActivity.class));
                    Toast.makeText(AuthActivity.this, "Bienvenido", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AuthActivity.this, "Usuario no existe", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AuthActivity.this, "Error al iniciar sesión", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(AuthActivity.this, HomeActivity.class));
            finish();
        }
    }

/*
    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Error al autenticar Usuario");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
        //AlertDialog alertDialog = new AlertDialog();
    }
*/

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