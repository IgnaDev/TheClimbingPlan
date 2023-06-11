package com.example.theclimbingplan.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.theclimbingplan.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    //Button btnMenuSesion, btnMenuHistorico;
    Button btnSignUp, btnLogIn, btnGoogle;
    EditText etEmail, etPassword;
    FirebaseAuth mAuth;
    private int GOOGLE_SIGN_IN = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        //btnMenuSesion = findViewById(R.id.btnEntrenoPrincipal);
        //btnMenuHistorico = findViewById(R.id.btnHistoricoPrincipal);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnGoogle = findViewById(R.id.btnGoogle);
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

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //configuracion
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient gsi = GoogleSignIn.getClient(AuthActivity.this, gso);
                gsi.signOut();
                startActivityForResult(gsi.getSignInIntent(), GOOGLE_SIGN_IN);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = null;
            try {
                account = task.getResult(ApiException.class);

                if (account != null) {
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                    });
                }
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(AuthActivity.this, "Error al iniciar sesión", Toast.LENGTH_LONG).show();

            }
        }
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

}