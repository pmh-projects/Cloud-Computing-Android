package com.example.projekt_bazaDanych;

import android.content.Intent;
import android.media.MediaPlayer;
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

import java.util.List;

public class update extends AppCompatActivity implements Validator.ValidationListener{

    EditText id, name, surname, phone, addAddress, addID;
    //Deklaracja obowiązku podania adresu
    @NotEmpty(message = "Pole adres nie może być puste")
    EditText address;
    //Walidacja wymuszająca wprowadzenie prawidłowego e-mail'u email@email.com
    @Email(message = "Wpisz poprawny adres e-mail")
    EditText  email;
    String stringId, stringName, stringSurname, stringAddress, stringPhone, stringEmail, sendID, sendAdres;
    Validator validatonCheck;
    Button validationOnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //wyciągniecie wartości z EditText
        name = findViewById(R.id.addName);
        surname = (EditText)findViewById(R.id.addSurname);
        phone = (EditText)findViewById(R.id.addPhone);
        email = (EditText)findViewById(R.id.addEmail);
        address = (EditText)findViewById(R.id.addAddress);

        showAllUserData();
       // Intent intent = getIntent();


        //dodanie wcześniej wprowadzonej wartości z Activity szukaj
//        addAddress=findViewById(R.id.addAddress);
//        sendAdres=getIntent().getExtras().getString("Adres");
//        addAddress.setText(sendAdres);

        //dodanie ID
        //addID=findViewById(R.id.addID);
        //sendID=getIntent().getExtras().getString("ID");
        //addID.setText(sendID);
        //id = (EditText)findViewById(R.id.addID);

        //Walidator do testowania zgodności pól adresu i e-mail'a z potrzebami
        validatonCheck = new Validator(this);
        validatonCheck.setValidationListener(this);

        //Walidacja po kliknięciu editBtn
        validationOnAdd = (Button) findViewById(R.id.editBtn);
        validationOnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validatonCheck.validate();
            }
        });

    }

    private void showAllUserData(){

        String _address = getIntent().getExtras().getString("StringAddress");
        String _email = getIntent().getExtras().getString("StringEmail");
        String _name = getIntent().getExtras().getString("StringName");
        String _phone = getIntent().getExtras().getString("StringPhone");
        String _surname = getIntent().getExtras().getString("StringSurname");

        name.setText(_name);
        surname.setText(_surname);
        address.setText(_address);
        phone.setText(_phone);
        email.setText(_email);

    }
    //przycisk cofania do poprzedniego Menu
    public void onBack(View view) {
        Intent backtoMenu = new Intent(this, search.class);
        startActivity(backtoMenu);
        finish();
    }


    //przycisk zamykający activity i program
    public void onClose(View view) {
        finish();
        System.exit(0);
    }

    //Walidacja danych; w wypadku sukcesu wysyłka danych do bazy. W innym wypadku pojawi się komunikat z błędem
    @Override
    public void onValidationSucceeded() {
        //stringId = id.getText().toString();
        stringName = name.getText().toString();
        stringSurname = surname.getText().toString();
        stringAddress = address.getText().toString();
        stringPhone = phone.getText().toString();
        stringEmail = email.getText().toString();




        //zmienna action,za pomocą której rozpoznawane jest działanie do wykonania w "silniku"
//        String action = "update";
//        workInBackground workInBackground = new workInBackground(this);
//        workInBackground.execute(action, stringId, stringName, stringSurname, stringAddress, stringPhone, stringEmail);
    }
    //W wypadku wprowadzenia błędnych danych pojawi się error, który jest zdefiniowany w message
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if(view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}