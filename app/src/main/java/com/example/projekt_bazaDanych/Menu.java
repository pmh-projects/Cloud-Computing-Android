package com.example.projekt_bazaDanych;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import static android.view.animation.Animation.INFINITE;

public class Menu extends AppCompatActivity {

//Menu "główne", w którym są do wyboru opcje: zamknij (program), szukaj(rekord po adresie), dodaj (nowy rekord)

   ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        image = findViewById(R.id.sunView);

        //translateAnimation, które wyświetla ruszający się obrazek.
        TranslateAnimation animation = new TranslateAnimation(-100.0f, 100.0f, .0f, 0.0f);
        animation.setDuration(1500);
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

    //"przycisk szukaj" - przejście do activity z możliwością wyszukania i edycji rekordu'u
    public void onSearch(View view) {
        Intent searchRecord = new Intent(this, CustomerList.class);
        finish();
        startActivity(searchRecord);
    }

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
        finish();
        startActivity(loginRecord);

    }

    public void onAddNA(View view) {
        Intent addNA = new Intent(this,addAccount.class);
        finish();
        startActivity(addNA);
    }
}