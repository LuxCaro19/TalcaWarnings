package com.example.talcawarningsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText txt_email, txt_password;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()==null){
            setContentView(R.layout.activity_login);
            txt_email = findViewById(R.id.login_txt_email);
            txt_password = findViewById(R.id.login_txt_password);
        }else{
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void launchRegister(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);

    }

    public void iniciarSesion(View view) {
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(LoginActivity.this,"Credenciales vacíos",Toast.LENGTH_LONG).show();
        }else{
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}