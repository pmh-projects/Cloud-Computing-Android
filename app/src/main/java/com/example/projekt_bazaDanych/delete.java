package com.example.projekt_bazaDanych;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class delete extends AppCompatActivity {

    EditText adres, id;
    String stringAddress, sendAdres, sendID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        adres = (EditText)findViewById(R.id.addAddress);
        id = (EditText)findViewById(R.id.sendID);

    }

    //Powrót do menu
    public void onBack(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    //Metoda onSearch wyszukująca dane użytkownika poprzez wysłanie zapytania do bazy wyszukującego record'y poprzez porównanie danych adresowych z stringAddress
    public void onSearch(View view) {

        stringAddress = adres.getText().toString();
        //zmienna action, za pomocą której rozpoznawane jest działanie do wykonania w "silniku"
        String action = "search_delete";

                workInBackground workInBackground = new workInBackground(this);
                workInBackground.execute(action, stringAddress);
    }

    //onEdit powoduje przejście do activity Update jednoczesnie wysyłając dane z pól Adres i Id do formularza edytującego
    public void onEdit(View view) {




//        sendID = id.getText().toString();
//        sendAdres = adres.getText().toString();
//        //zmienna action, za pomocą której rozpoznawane jest działanie do wykonania w "silniku"
//        String action = "delete";
//
//        workInBackground workInBackground = new workInBackground(this);
//        workInBackground.execute(action, sendID, sendAdres);
    }
}