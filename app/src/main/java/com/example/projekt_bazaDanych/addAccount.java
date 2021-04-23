package com.example.projekt_bazaDanych;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addAccount extends AppCompatActivity {

    EditText sName, sPassword;
    Button btnLogin;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaccount);

        sName = (EditText)findViewById(R.id.introLogin);
        sPassword = (EditText)findViewById(R.id.introPassword);
        btnLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.i("Info", "Zalogowany");
                }else{
                    Log.i("Info","Niezalogowany");
                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailString = sName.getText().toString();
                String passwordString = sPassword.getText().toString();

                if(!emailString.equals("") && !passwordString.equals("")) {
                    mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(addAccount.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(addAccount.this, "Proszę spróbować ponownie", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(addAccount.this, "Nowe konto zostało utworzone", Toast.LENGTH_LONG).show();

                                Intent backtoMenu = new Intent(addAccount.this, Menu.class);
                                finishAndRemoveTask();
                                startActivity(backtoMenu);
                            }
                        }
                    });
                }
            }
        });


        sName = (EditText)findViewById(R.id.introLogin);
        sPassword = (EditText)findViewById(R.id.introPassword);
        btnLogin = findViewById(R.id.btnLogin);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



    public void onClose(View view) {
        Intent intent = new Intent(this, Menu.class);
        finish();
        startActivity(intent);
    }
}
