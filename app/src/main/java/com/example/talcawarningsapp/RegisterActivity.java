package com.example.talcawarningsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.talcawarningsapp.model.Usuario;
import com.example.talcawarningsapp.ui.main.NuevoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText txt_email, txt_nombre,txt_celular, txt_password;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txt_email = findViewById(R.id.register_txt_email);
        txt_nombre = findViewById(R.id.register_txt_nombre);
        txt_celular = findViewById(R.id.register_txt_numero);
        txt_password = findViewById(R.id.register_txt_password);
        auth = FirebaseAuth.getInstance();
    }

    public void crearCuenta(View view) {
        final String email,nombre,celular,password;
        email = txt_email.getText().toString();
        nombre = txt_nombre.getText().toString();
        celular = txt_celular.getText().toString();
        password = txt_password.getText().toString();
        if(email.isEmpty() || nombre.isEmpty() || celular.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Los campos están vacíos",Toast.LENGTH_LONG).show();
        }else{
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Usuarios");
                                Usuario user = new Usuario();
                                user.setNombre(nombre);
                                user.setCelular(celular);
                                user.setEmail(email);
                                user.setUid(task.getResult().getUser().getUid());
                                myRef.push().setValue(user);
                                Toast.makeText(RegisterActivity.this,"Cuenta creada correctamente",Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(RegisterActivity.this,"Error al crear la cuenta",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void volverAlLogin(View view) {

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}