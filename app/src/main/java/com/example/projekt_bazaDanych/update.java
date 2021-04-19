package com.example.projekt_bazaDanych;

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
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

public class update extends AppCompatActivity {
    //implements Validator.ValidationListener
    EditText id, name, surname, phone, addAddress, addID;
    //Deklaracja obowiązku podania adresu
    @NotEmpty(message = "Pole adres nie może być puste")
    EditText address;
    //Walidacja wymuszająca wprowadzenie prawidłowego e-mail'u email@email.com
    @Email(message = "Wpisz poprawny adres e-mail")
    EditText email, searchVariable;
    String stringId, stringName, stringSurname, stringAddress, stringPhone, stringEmail, sendID, sendAdres;
    Validator validatonCheck;
    Button validationOnAdd, searchBtn, editBtn, deleteBtn;
    TextView adresUsera;
    String stringAddressEmail, sendAdresEmail;
    DatabaseReference reff;
    private FirebaseDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        adresUsera = (EditText) findViewById(R.id.searchVariable);
        //wyciągniecie wartości z EditText
        name = (EditText) findViewById(R.id.addName);
        surname = (EditText) findViewById(R.id.addSurname);
        phone = (EditText) findViewById(R.id.addPhone);
        email = (EditText) findViewById(R.id.addEmail);
        address = (EditText) findViewById(R.id.addAddress);

        searchBtn = (Button) findViewById(R.id.searchBtn);
        editBtn = (Button) findViewById(R.id.editBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        // showAllUserData();
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

//        Walidator do testowania zgodności pól adresu i e-mail'a z potrzebami
//        validatonCheck = new Validator(this);
//        validatonCheck.setValidationListener(this);
//
//        //Walidacja po kliknięciu editBtn
//        validationOnAdd = (Button) findViewById(R.id.editBtn);
//        validationOnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                validatonCheck.validate();
//            }
//        });

        searchBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                String childName = adresUsera.getText().toString().trim();
                reff = FirebaseDatabase.getInstance().getReference().child("customers").child(childName);

                if (childName.equals("")) {
                    Toast.makeText(update.this, "Puste pole. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();
                } else {
                    reff.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String addressfromDB = dataSnapshot.child("stringAddress").getValue(String.class);
                                String emailfromDB = dataSnapshot.child("stringEmail").getValue(String.class);
                                String namefromDB = dataSnapshot.child("stringName").getValue(String.class);
                                String phonefromDB = dataSnapshot.child("stringPhone").getValue(String.class);
                                String surnamefromDB = dataSnapshot.child("stringSurname").getValue(String.class);

                                //adresUsera.setText(namefromDB);
                                name.setText(namefromDB);
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
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                String childName = adresUsera.getText().toString().trim();
                reff = FirebaseDatabase.getInstance().getReference().child("customers").child(childName);

                if (childName.equals("")) {
                    Toast.makeText(update.this, "Puste pole. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();
                } else {
                reff.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String Name = name.getText().toString().trim();
                                String Surname = surname.getText().toString().trim();
                                String Address = address.getText().toString().trim();
                                String Phone = phone.getText().toString().trim();
                                String Email = email.getText().toString().trim();

                                Customer updateCustomer = new Customer(Name, Surname, Address, Phone, Email);

//                                DatabaseReference node=db.getReference("customers");
//                                node.child(stringName).setValue(updateCustomer);

                                reff.setValue(updateCustomer);

//                                name.getText().clear();
//                                surname.getText().clear();
//                                address.getText().clear();
//                                phone.getText().clear();
//                                email.getText().clear();

                                Toast.makeText(getApplicationContext(), "Zaktualizowano", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(update.this, "Wprowadzono błędne dane. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                }
            }
        });
//                    {
//
//                    String Name = name.getText().toString().trim();
//                    String Surname = surname.getText().toString().trim();
//                    String Address = address.getText().toString().trim();
//                    String Phone = phone.getText().toString().trim();
//                    String Email = email.getText().toString().trim();
//
//                    Customer updateCustomer = new Customer(Name, Surname, Address, Phone, Email);
//                    reff.setValue(updateCustomer);
//
//                    Toast.makeText(getApplicationContext(), "Zaktualizowano", Toast.LENGTH_LONG).show();
//                }


        deleteBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){

                String childName = adresUsera.getText().toString().trim();
                reff = FirebaseDatabase.getInstance().getReference().child("customers").child(childName);

                if (childName.equals("")) {
                    Toast.makeText(update.this, "Puste pole. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();
                } else {
                    reff.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String Name = name.getText().toString().trim();
                                String Surname = surname.getText().toString().trim();
                                String Address = address.getText().toString().trim();
                                String Phone = phone.getText().toString().trim();
                                String Email = email.getText().toString().trim();


                                reff.removeValue();
                                adresUsera.setText("");
                                name.getText().clear();
                                surname.getText().clear();
                                address.getText().clear();
                                phone.getText().clear();
                                email.getText().clear();
                                adresUsera.setText("");
                                Toast.makeText(getApplicationContext(), "Usunięto użytkownika", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(update.this, "Wprowadzono błędne dane. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                }
            }
        });



//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String childName = adresUsera.getText().toString().trim();
//                reff = FirebaseDatabase.getInstance().getReference().child("customers").child(childName);
//
//                if (childName.equals("")) {
//                    Toast.makeText(update.this, "Puste pole. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();
//                } else {
//                    reff.addValueEventListener(new ValueEventListener() {
//
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                String Name = name.getText().toString().trim();
//                                String Surname = surname.getText().toString().trim();
//                                String Address = address.getText().toString().trim();
//                                String Phone = phone.getText().toString().trim();
//                                String Email = email.getText().toString().trim();
//
//                                Customer updateCustomer = new Customer(Name, Surname, Address, Phone, Email);
//                                reff.setValue(updateCustomer);
//
//                                name.getText().clear();
//                                surname.getText().clear();
//                                address.getText().clear();
//                                phone.getText().clear();
//                                email.getText().clear();
//
//                                Toast.makeText(getApplicationContext(), "Zaktualizowano", Toast.LENGTH_LONG).show();
//                            } else {
//                                Toast.makeText(update.this, "Wprowadzono błędne dane. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//
//                    });
//                }
//            }
//        });
//                    {
//
//                    String Name = name.getText().toString().trim();
//                    String Surname = surname.getText().toString().trim();
//                    String Address = address.getText().toString().trim();
//                    String Phone = phone.getText().toString().trim();
//                    String Email = email.getText().toString().trim();
//
//                    Customer updateCustomer = new Customer(Name, Surname, Address, Phone, Email);
//                    reff.setValue(updateCustomer);
//
//                    Toast.makeText(getApplicationContext(), "Zaktualizowano", Toast.LENGTH_LONG).show();
//                }

//
//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String childName = adresUsera.getText().toString().trim();
//                reff = FirebaseDatabase.getInstance().getReference().child("customers").child(childName);
//
//                if (childName.equals("")) {
//                    Toast.makeText(update.this, "Puste pole. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();
//                } else {
//                    reff.addValueEventListener(new ValueEventListener() {
//
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                String Name = name.getText().toString().trim();
//                                String Surname = surname.getText().toString().trim();
//                                String Address = address.getText().toString().trim();
//                                String Phone = phone.getText().toString().trim();
//                                String Email = email.getText().toString().trim();
//
//
//                                reff.removeValue();
//                                adresUsera.setText("");
//                                name.getText().clear();
//                                surname.getText().clear();
//                                address.getText().clear();
//                                phone.getText().clear();
//                                email.getText().clear();
//                                adresUsera.setText("");
//                                Toast.makeText(getApplicationContext(), "Usunięto użytkownika", Toast.LENGTH_LONG).show();
//                            } else {
//                                Toast.makeText(update.this, "Wprowadzono błędne dane. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//
//                    });
//                }
//            }
//        });

    }
//                    {
//
//                    String Name = name.getText().toString().trim();
//                    String Surname = surname.getText().toString().trim();
//                    String Address = address.getText().toString().trim();
//                    String Phone = phone.getText().toString().trim();
//                    String Email = email.getText().toString().trim();
//
//                    Customer updateCustomer = new Customer(Name, Surname, Address, Phone, Email);
//                    reff.setValue(updateCustomer);
//
//                    Toast.makeText(getApplicationContext(), "Zaktualizowano", Toast.LENGTH_LONG).show();
//                }


//                DatabaseReference node=db.getReference("customers");
//                Query checkAddress = node.orderByChild("stringName").equalTo("Marcin");
//                checkAddress.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        String addressfromDB = dataSnapshot.child("stringAddress").getValue().toString();
//                        String emailfromDB = dataSnapshot.child("stringEmail").getValue(String.class);
//                        String namefromDB = dataSnapshot.child("stringName").getValue().toString();
//                        String phonefromDB = dataSnapshot.child("stringPhone").getValue(String.class);
//                        String surnamefromDB = dataSnapshot.child("stringSurname").getValue(String.class);
//
//                        adresUsera.setText(namefromDB);
//                        name.setText(addressfromDB);
//                        surname.setText(surnamefromDB);
//                        address.setText(namefromDB);
//                        phone.setText(phonefromDB);
//                        email.setText(emailfromDB);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


//
//    public void onEdit(View view) {
//
//        final String userAddress = adresUsera.getText().toString().trim();
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customers").child(userAddress);
//        Query checkAddress = databaseReference.orderByChild("StringAddress").equalTo(userAddress);
//        checkAddress.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
////                if(dataSnapshot.exists()){
//
//                String addressfromDB = dataSnapshot.child(userAddress).child("StringAddress").getValue().toString();
//                String emailfromDB = dataSnapshot.child(userAddress).child("StringEmail").getValue().toString();
//                String namefromDB = dataSnapshot.child(userAddress).child("StringName").getValue().toString();
//                String phonefromDB = dataSnapshot.child(userAddress).child("StringPhone").getValue().toString();
//                String surnamefromDB = dataSnapshot.child(userAddress).child("StringSurname").getValue().toString();
//
//
//                name.setText(addressfromDB);
//                surname.setText(surnamefromDB);
//                address.setText(namefromDB);
//                phone.setText(phonefromDB);
//                email.setText(emailfromDB);
////                Intent intent = new Intent(getApplicationContext(), update.class);
////
////
////                intent.putExtra("StringAddress", addressfromDB);
////                intent.putExtra("StringEmail", emailfromDB);
////                intent.putExtra("StringName", namefromDB);
////                intent.putExtra("StringPhone", phonefromDB);
////                intent.putExtra("StringSurname", surnamefromDB);
////
////                startActivity(intent);
//
//                // }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//    private void showAllUserData(){
//
//        String _address = getIntent().getExtras().getString("StringAddress");
//        String _email = getIntent().getExtras().getString("StringEmail");
//        String _name = getIntent().getExtras().getString("StringName");
//        String _phone = getIntent().getExtras().getString("StringPhone");
//        String _surname = getIntent().getExtras().getString("StringSurname");
//
//        name.setText(_name);
//        surname.setText(_surname);
//        address.setText(_address);
//        phone.setText(_phone);
//        email.setText(_email);
//
//    }


    //przycisk cofania do poprzedniego Menu
    public void onBack(View view) {
        Intent backtoMenu = new Intent(this, Menu.class);
        startActivity(backtoMenu);
        finish();
    }


    //przycisk zamykający activity i program
    public void onClose(View view) {
        finish();
        System.exit(0);
    }
}
    //Walidacja danych; w wypadku sukcesu wysyłka danych do bazy. W innym wypadku pojawi się komunikat z błędem
//    @Override
//    public void onValidationSucceeded() {
//        //stringId = id.getText().toString();
//        stringName = name.getText().toString();
//        stringSurname = surname.getText().toString();
//        stringAddress = address.getText().toString();
//        stringPhone = phone.getText().toString();
//        stringEmail = email.getText().toString();
//
//
//
//
//        //zmienna action,za pomocą której rozpoznawane jest działanie do wykonania w "silniku"
////        String action = "update";
////        workInBackground workInBackground = new workInBackground(this);
////        workInBackground.execute(action, stringId, stringName, stringSurname, stringAddress, stringPhone, stringEmail);
//    }
    //W wypadku wprowadzenia błędnych danych pojawi się error, który jest zdefiniowany w message
//    @Override
//    public void onValidationFailed(List<ValidationError> errors) {
//        for(ValidationError error : errors) {
//            View view = error.getView();
//            String message = error.getCollatedErrorMessage(this);
//            if(view instanceof EditText) {
//                ((EditText) view).setError(message);
//            } else {
//                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//            }
//        }
//    }
