package com.example.talcawarningsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    TextView nom_app, hecho, nom_luis, nom_richard;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        Animation animacion_arriba = AnimationUtils.loadAnimation(this,R.anim.anim_arriba);
        Animation animacion_abajo = AnimationUtils.loadAnimation(this,R.anim.anim_abajo);

        nom_app = findViewById(R.id.splash_nombre_app);
        hecho= findViewById(R.id.splash_nombre_hecho);
        nom_luis = findViewById(R.id.splash_nombre_luis);
        nom_richard = findViewById(R.id.splash_nombre_richard);
        logo = findViewById(R.id.splash_logo);

        nom_app.setAnimation(animacion_abajo);
        hecho.setAnimation(animacion_abajo);
        nom_luis.setAnimation(animacion_abajo);
        nom_richard.setAnimation(animacion_abajo);
        logo.setAnimation(animacion_arriba);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        }, 5000);




    }
}