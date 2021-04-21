package com.example.projekt_bazaDanych;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.Query;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;




public class addCustomer extends AppCompatActivity implements Validator.ValidationListener {

    //Wymuszenie wpisania wartości do pola address
    @NotEmpty(message = "Pole nie może być puste")
    EditText address, name, surname, phone;
    //Wymuszenie wpisania poprawnej wartości do pola email
    @Email(message = "Wpisz poprawny adres e-mail")
    EditText  email;
    @NotEmpty(message = "Pole nie może być puste")
    String Name, Surname, Address, Phone, Email;
    Validator validatonCheck;
    Button validationOnAdd;
    DatabaseReference base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Wyciągniecie treści z obrazka
        name = (EditText) findViewById(R.id.addName);
        surname = (EditText) findViewById(R.id.addSurname);
        address = (EditText) findViewById(R.id.addAddress);
        phone = (EditText) findViewById(R.id.addPhone);
        email = (EditText) findViewById(R.id.addEmail);

        //Walidator do testowania zgodności pól adresu i e-mail'a z potrzebami
        validatonCheck = new Validator(this);
        validatonCheck.setValidationListener(this);

        //Walidacja po kliknięciu addBtn
        validationOnAdd = (Button) findViewById(R.id.addBtn);
        validationOnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validatonCheck.validate();
            }
        });
    }

    //powrót do Menu
    public void onBack(View view) {

        Intent backtoMenu = new Intent(this, Menu.class);
        finishAndRemoveTask();
        startActivity(backtoMenu);
    }



    @Override
    public void onValidationSucceeded() {

        Name = name.getText().toString().trim();
        Surname = surname.getText().toString().trim();
        Address = address.getText().toString().trim();
        Phone = phone.getText().toString().trim();
        Email = email.getText().toString().trim();

        base = FirebaseDatabase.getInstance().getReference().child("customers").child(Name);

        base.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "Użytkownik o podanej nazwie istnieje w bazie.", Toast.LENGTH_LONG).show();

                }else{

                    Customer newCustomer = new Customer(Name, Surname, Address, Phone, Email);

                    FirebaseDatabase newCustomerDatabase = FirebaseDatabase.getInstance();

                    DatabaseReference base= newCustomerDatabase.getReference("customers");
                    //dodanie nazwy głównej wpisu w bazie
                    base.child(Name).setValue(newCustomer);

                    Toast.makeText(getApplicationContext(), "Dodano nowe dane.", Toast.LENGTH_LONG).show();
                    Intent backtoMenu = new Intent(addCustomer.this, Menu.class);
                    finishAndRemoveTask();
                    startActivity(backtoMenu);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    //W wypadku wprowadzenia błędnych danych pojawi się error, który jest zdefiniowany w message
    @Override
    public void onValidationFailed(List<ValidationError> errs) {
        for(ValidationError err : errs) {
            View view = err.getView();
            String infoMess = err.getCollatedErrorMessage(this);
            if(view instanceof EditText) {
                ((EditText) view).setError(infoMess);
            } else {
                Toast.makeText(this, infoMess, Toast.LENGTH_LONG).show();
            }
        }
    }
}