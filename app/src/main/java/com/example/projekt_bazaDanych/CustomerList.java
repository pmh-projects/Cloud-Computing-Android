package com.example.projekt_bazaDanych;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerList extends AppCompatActivity {
private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listcustomers);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview_customers);

        new FirebaseDatabaseHelper().readCustomers(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Customer> customers, List<String> keys) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                new recyclerViewList().setConfig(mRecyclerView,CustomerList.this, customers,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_customermenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.deleteBtn:
                Intent backToMenu = new Intent(this, com.example.projekt_bazaDanych.Menu.class);
                finish();
                startActivity(backToMenu);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
