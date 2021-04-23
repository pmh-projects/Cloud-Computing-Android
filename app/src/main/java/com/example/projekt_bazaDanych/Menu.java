package com.example.projekt_bazaDanych;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.view.animation.Animation.INFINITE;

public class Menu extends AppCompatActivity {

//Menu "główne", w którym są do wyboru opcje: zamknij (program), szukaj(rekord po adresie), dodaj (nowy rekord)

   ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        image = findViewById(R.id.iconView);

        //translateAnimation, które wyświetla ruszający się obrazek.
        //Ustawienie pozycji ruchu
        TranslateAnimation animation = new TranslateAnimation(-450.0f, 450.0f, .0f, 0.0f);
        //Ustawienia prędkości i ilości powtórzeń
        animation.setDuration(2900);
        animation.setRepeatCount(INFINITE);
        animation.setRepeatMode(2);
        animation.setFillAfter(true);
        image.startAnimation(animation);

    }

    //metoda void "przycisk dodaj" powoduje przejście do activity z możliwością dodania record'u
    public void onAdd(View view) {
        Intent addRecord = new Intent(this, addCustomer.class);
        finish();
        startActivity(addRecord);
    }

    //Przejście do activity z listą użytkowników
    public void onSearch(View view) {
        Intent searchRecord = new Intent(this, showCustomers.class);
        finish();
        startActivity(searchRecord);
    }
    //Przejście do edycji
    public void onEdit(View view){

        Intent edit = new Intent(this, update.class);
        finish();
        startActivity(edit);
    }
    //wyjście i zamknięcie programu.
    public void onClose(View view) {
        finishAffinity();
        System.exit(0);
    }

    public void onLogout(View view) {
        Intent loginRecord = new Intent(this,MainActivity.class);
        Toast.makeText(getApplicationContext(), "Wylogowano", Toast.LENGTH_LONG).show();
        finish();
        startActivity(loginRecord);

    }

    //Przejście do dodawania nowego użytkownika
    public void onAddNA(View view) {
        Intent addNA = new Intent(this,addAccount.class);
        finish();
        startActivity(addNA);
    }
}