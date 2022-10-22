package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

public class Rating_activity extends AppCompatActivity {

    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TP_FINAL_TemaPopUp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratingBar= findViewById(R.id.ratingBarPopUp);

        ratingBar.setNumStars(5);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getWindow().setLayout((int) (metrics.widthPixels * 0.85),(int) (metrics.heightPixels * 0.55));


    }


    public void clickClose(View view){
        this.finish();
    }

    public void clickSend(View view){
        this.finish();
        Toast.makeText(getApplicationContext(),"Gracias por su opinión. Usted calificó con " +String.valueOf(ratingBar.getRating()) + " estrellas.",Toast.LENGTH_SHORT).show();
    }
}