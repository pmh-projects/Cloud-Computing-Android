package com.example.projekt_bazaDanych;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Optional;

import java.util.List;

import static java.security.AccessController.getContext;

public class update extends AppCompatActivity implements Validator.ValidationListener{
    //implements Validator.ValidationListener
    @NotEmpty(message = "Pole nie może być puste")
    EditText address, name, surname, phone;
    //Walidacja wymuszająca wprowadzenie prawidłowego e-mail'u email@email.com
    @Email(message = "Wpisz poprawny adres e-mail")
    EditText email;
    @Optional
    EditText searchVariable;
    @Email(message = "Wpisz poprawny adres e-mail")
    public String Email;
    Validator validatonCheck;
    Button validationOnEdit, searchBtn, editBtn, deleteBtn;
    EditText userUniqe;
    DatabaseReference base2, base3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        userUniqe = (EditText) findViewById(R.id.searchVariable);
        //wyciągniecie wartości z EditText
        name = (EditText) findViewById(R.id.addName);
        surname = (EditText) findViewById(R.id.addSurname);
        phone = (EditText) findViewById(R.id.addPhone);
        email = (EditText) findViewById(R.id.addEmail);
        address = (EditText) findViewById(R.id.addAddress);

        searchBtn = (Button) findViewById(R.id.searchBtn);
        editBtn = (Button) findViewById(R.id.editBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        //Walidator do testowania zgodności pól adresu i e-mail'a z potrzebami
        validatonCheck = new Validator(this);
        validatonCheck.setValidationListener(update.this);

        //Walidacja po kliknięciu addBtn
        validationOnEdit = (Button) findViewById(R.id.editBtn);
        validationOnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validatonCheck.validate();

            }
        });
    }

    //Update danych po udanej walidacji
    @Override
    public void onValidationSucceeded() {

                String searchValue = userUniqe.getText().toString().trim();
                base2 = FirebaseDatabase.getInstance().getReference().child("customers").child(searchValue);

                if (searchValue.equals("")) {
                    Toast.makeText(update.this, "Nie wprowadzono nazwy użytkownika do edycji. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();
                } else {
                    base2.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot Snapshot) {
                            if (Snapshot.exists()) {

                                AlertDialog.Builder message = new AlertDialog.Builder(update.this);
                                message.setMessage("Czy napewno chesz edytować dane?");
                                message.setCancelable(true);

                                message.setPositiveButton(
                                        "Tak",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                            String UniqueName = userUniqe.getText().toString().trim();
                            String NameSurname = surname.getText().toString().trim();
                            String Address = address.getText().toString().trim();
                            String Phone = phone.getText().toString().trim();
                            Email = email.getText().toString().trim();

                            Customer updateCustomer = new Customer(UniqueName, NameSurname, Address, Phone, Email);

                            base2.setValue(updateCustomer);

                            Toast.makeText(getApplicationContext(), "Zaktualizowano dane.", Toast.LENGTH_LONG).show();
                                Intent backtoMenu = new Intent(update.this, Menu.class);
                                finishAndRemoveTask();
                                startActivity(backtoMenu);

                                            }
                                        });

                                message.setNegativeButton(
                                        "Cofnij",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert = message.create();
                                alert.show();

                            } else {
                                Toast.makeText(update.this, "Wprowadzono błędne dane do edycji. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

    @Override
    public void onValidationFailed(List<ValidationError> errs) {
        for(ValidationError err : errs) {
            View view = err.getView();
            String message = err.getCollatedErrorMessage(this);
            if(view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    //Usuwanie użytkownika po kliknięciu przycisku Delete
    public void onDelete (View view){

                String searchValue = userUniqe.getText().toString().trim();
                base3 = FirebaseDatabase.getInstance().getReference().child("customers").child(searchValue);

        if(!searchValue.equals("")){
                base3.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Email = email.getText().toString().trim();

                            //utworzenie warunku usuwania - wymusza to wyszukanie użytkownika. Zabezpiecza to przed przypadkowym wpisaniem użytkownika i usunięciem.
                            if (dataSnapshot.exists() && !Email.equals("")) {

                                AlertDialog.Builder message = new AlertDialog.Builder(update.this);
                                message.setMessage("Czynność jest nieodwracalna. Czy napewno chcesz usunąć użytkownika?");
                                message.setCancelable(true);

                                message.setPositiveButton(
                                        "Tak",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                base3.removeValue();

                                                Toast.makeText(getApplicationContext(), "Usunięto użytkownika", Toast.LENGTH_LONG).show();
                                                Intent backtoMenu = new Intent(update.this, Menu.class);
                                                finishAndRemoveTask();
                                                startActivity(backtoMenu);
                                            }
                                        });

                                message.setNegativeButton(
                                        "Cofnij",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert = message.create();
                                alert.show();

                            }else{

                                Toast.makeText(getApplicationContext(), "Wprowadzono niepełne lub niepoprawne dane. Wyszukaj jeszcze raz", Toast.LENGTH_LONG).show();

                            }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }else{

            Toast.makeText(getApplicationContext(), "Wprowadź dane do usunięcia", Toast.LENGTH_LONG).show();

        }
    }

    //Meotda wyszukująca użytkownika
    public void onSearch(View view){
        String searchValue = userUniqe.getText().toString().trim();
        base3 = FirebaseDatabase.getInstance().getReference().child("customers").child(searchValue);

        if (searchValue.equals("")) {

            Toast.makeText(update.this, "Puste pole. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();

        } else {
            base3.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String addressfromDB = dataSnapshot.child("address").getValue(String.class);
                        String emailfromDB = dataSnapshot.child("email").getValue(String.class);
                        String phonefromDB = dataSnapshot.child("phone").getValue(String.class);
                        String surnamefromDB = dataSnapshot.child("nameSurname").getValue(String.class);
                        String searchValue = userUniqe.getText().toString().trim();
                        //userEmail.setText(namefromDB);
                        name.setText(searchValue);
                        surname.setText(surnamefromDB);
                        address.setText(addressfromDB);
                        phone.setText(phonefromDB);
                        email.setText(emailfromDB);

                    } else {
                        Toast.makeText(update.this, "Brak wyników. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void onBack (View view){

        Intent backtoMenu = new Intent(update.this, Menu.class);
        finishAndRemoveTask();
        startActivity(backtoMenu);

    }
}

