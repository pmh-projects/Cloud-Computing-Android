package com.example.projekt_bazaDanych;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;




public class add extends AppCompatActivity implements Validator.ValidationListener {

    EditText name, surname, phone;
    //Wymuszenie wpisania wartości do pola address
    @NotEmpty(message = "Pole adres nie może być puste")
    EditText address;
    //Wymuszenie wpisania poprawnej wartości do pola email
    @Email(message = "Wpisz poprawny adres e-mail")
    EditText  email;
    String stringName, stringSurname, stringAddress, stringPhone, stringEmail;
    Validator validatonCheck;
    Button validationOnAdd;


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
        startActivity(backtoMenu);
        finish();
    }


    //Walidacja danych; w wypadku sukcesu wysyłka danych do bazy. W innym wypadku pojawi się komunikat z błędem
    @Override
    public void onValidationSucceeded() {

        stringName = name.getText().toString().trim();
        stringSurname = surname.getText().toString().trim();
        stringAddress = address.getText().toString().trim();
        stringPhone = phone.getText().toString().trim();
        stringEmail = email.getText().toString().trim();

        Customer obj = new Customer(stringName, stringSurname, stringAddress, stringPhone, stringEmail);

        FirebaseDatabase db=FirebaseDatabase.getInstance();

        DatabaseReference node=db.getReference("customers");
        node.child(stringName).setValue(obj);

        name.setText("");
        surname.setText("");
        address.setText("");
        phone.setText("");
        email.setText("");

        Toast.makeText(getApplicationContext(), "Dodano", Toast.LENGTH_LONG).show();


        //zmienna action, za pomocą której rozpoznawane jest działanie do wykonania w "silniku"
//        String action = "sendData";
//
//        workInBackground workInBackground = new workInBackground(this);
//        workInBackground.execute(action, stringName, stringSurname, stringAddress, stringPhone, stringEmail);

    }
    //W wypadku wprowadzenia błędnych danych pojawi się error, który jest zdefiniowany w message
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
}