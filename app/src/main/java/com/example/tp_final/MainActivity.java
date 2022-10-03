package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imgWord, imgArrow, imgHat, imgText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        imgArrow= (ImageView) findViewById(R.id.imgArrow);
        imgHat= (ImageView) findViewById(R.id.imgHat);
        imgWord= (ImageView) findViewById(R.id.imgWord);
        imgText= (ImageView) findViewById(R.id.imgText);

        Animation Animacion_Abajo = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);
        Animation Animacion_Arriba = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);

        imgHat.setAnimation(Animacion_Arriba);
        imgWord.setAnimation(Animacion_Abajo);
        imgArrow.setAnimation(Animacion_Abajo);
        imgText.setAnimation(Animacion_Abajo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,Seleccion_Usuario.class);
                startActivity(i);
                finish();
            }
        },4000);



    }
}