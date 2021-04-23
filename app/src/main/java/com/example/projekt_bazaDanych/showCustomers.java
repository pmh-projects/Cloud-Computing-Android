package com.example.projekt_bazaDanych;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class showCustomers extends AppCompatActivity {

    DatabaseReference baseRef;
    ListView listView;
    ArrayList<String> arrayCustomers = new ArrayList<>();
    ArrayAdapter<String> aAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcustomers);

        //Wyciąganie listy klientów z bazy
        baseRef= FirebaseDatabase.getInstance().getReference("customers");
        listView=(ListView) findViewById(R.id.listcustomers);
        aAdapter=new ArrayAdapter<String>(this,R.layout.textviewlist,arrayCustomers);
        listView.setAdapter(aAdapter);
        baseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //Wyciągnięcie listy klientów z udziałem stringCustomer()
                String data = snapshot.getValue(Customer.class).stringCustomer();
                arrayCustomers.add(data);
                aAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Odwołanie do Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_customermenu, (android.view.Menu) menu);
        return true;
    }

    //Switch z przyciskiem Cofnij w Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.backBtn:
                Intent backToMenu = new Intent(this, com.example.projekt_bazaDanych.Menu.class);
                finish();
                startActivity(backToMenu);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
