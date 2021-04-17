package com.example.projekt_bazaDanych;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class search extends AppCompatActivity {

    EditText adresUsera, id;
    String stringAddressEmail, sendAdresEmail, sendID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        adresUsera= (EditText)findViewById(R.id.addAddress);
        //id = (EditText)findViewById(R.id.sendID);

    }

    //Powrót do menu
    public void onBack(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    //Metoda onSearch wyszukująca dane użytkownika poprzez wysłanie zapytania do bazy wyszukującego record'y poprzez porównanie danych adresowych z stringAddress
//    public void onSearch(View view) {
//
//        stringAddressEmail = adresEmail.getText().toString();
//        //zmienna action, za pomocą której rozpoznawane jest działanie do wykonania w "silniku"
//        String action = "search";
//
////                workInBackground workInBackground = new workInBackground(this);
////                workInBackground.execute(action, stringAddress);
//    }

    //onEdit powoduje przejście do activity Update jednoczesnie wysyłając dane z pól Adres i Id do formularza edytującego

    public void onEdit(View view) {

        final String userAddress = adresUsera.getText().toString().trim();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customers").child(userAddress);
        Query checkAddress = databaseReference.orderByChild("StringAddress").equalTo(userAddress);
        checkAddress.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                if(dataSnapshot.exists()){

                String addressfromDB = dataSnapshot.child(userAddress).child("StringAddress").getValue(String.class);
                String emailfromDB = dataSnapshot.child(userAddress).child("StringEmail").getValue(String.class);
                String namefromDB = dataSnapshot.child(userAddress).child("StringName").getValue(String.class);
                String phonefromDB = dataSnapshot.child(userAddress).child("StringPhone").getValue(String.class);
                String surnamefromDB = dataSnapshot.child(userAddress).child("StringSurname").getValue(String.class);

                Intent intent = new Intent(getApplicationContext(), update.class);

                Log.i(surnamefromDB, "infoi");
                intent.putExtra("StringAddress", addressfromDB);
                intent.putExtra("StringEmail", emailfromDB);
                intent.putExtra("StringName", namefromDB);
                intent.putExtra("StringPhone", phonefromDB);
                intent.putExtra("StringSurname", surnamefromDB);

                startActivity(intent);

                // }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }}

//    }
//    public void onEdit(View view) {
//
//
////        Intent edit = new Intent(this, update.class);
////
////        //wysylka wartosci Adres o nazwie i kluczu Adres, po którym jest przechwytywana w nastepnym activity
////        edit.putExtra ( "Adres", adresEmail.getText().toString() );
////        sendAdresEmail = adresEmail.getText().toString();
////        edit.putExtra("Adres",sendAdresEmail);
////
////        //wysylka wartosci ID o nazwie i kluczu OD, po którym jest przechwytywana w nastepnym activity
////        edit.putExtra ( "ID", id.getText().toString() );
////        sendID = id.getText().toString();
////        edit.putExtra("ID",sendID);
////
////            startActivity(edit);
////            finish();
//
//    }
//}