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

    FirebaseDatabase database;
    DatabaseReference databaseReference;
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


//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue(String.class);
//                Toast.makeText(addAccount.this, value, Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.i("Info", "Użytkownik jest zalogowany");
                }else{
                    Log.i("Info","Użytkownik nie jest zalogowany");
                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("Info","Kliknieto");
                String emailString = sName.getText().toString();
                String passwordString = sPassword.getText().toString();

                if(!emailString.equals("") && !passwordString.equals("")) {
                    mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(addAccount.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(addAccount.this, "Nie udało sie założyc konta", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(addAccount.this, "Nowe konto zostało utworzone", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });


        sName = (EditText)findViewById(R.id.introLogin);
        sPassword = (EditText)findViewById(R.id.introPassword);
        btnLogin = findViewById(R.id.btnLogin);
        //Video do intra
//        videoIntro = findViewById(R.id.videoIntro);
//        videoIntro.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sample3);
//
//        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(videoIntro);
//        videoIntro.setMediaController(mediaController);
//
//        videoIntro.start();
//
//        //Muzyka do intra i ustawienie głośności
//        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.eko_theme);
//        mediaPlayer.setVolume(.1f, .1f);
//        //zapętlenie
//        mediaPlayer.setLooping(true);
//        mediaPlayer.start();

        //Petla video -> dzięki temu fragmentowi video po zakończeniu uruchamia się od nowa
//        videoIntro.setOnCompletionListener ( new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                videoIntro.start();
//            }
//        });
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

//    public void OnLogin(View view){
//
//        //pobiera tekst z edittext i wysyła do działającego w tle activity
//        String name = Name.getText().toString();
//        String password = Password.getText().toString();
//        //zmienna action, za pomocą której rozpoznawane jest działanie do wykonania w "silniku"
//        String action = "login";
//
//        //instancja klasy workInBackground z konkteksem this i przekazanie wartości to workInBackground do egzekucji
//        workInBackground workInBackground = new workInBackground(this);
//        workInBackground.execute(action, name, password);
//
//         }

    public void onClose(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}