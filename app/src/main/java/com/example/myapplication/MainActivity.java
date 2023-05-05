package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 42;
    private FirebaseFirestore mFirestore;
    private CollectionReference users;

    EditText emailText;
    EditText passwordText;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.emailInput);
        passwordText = findViewById(R.id.passwordInput);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        mFirestore = FirebaseFirestore.getInstance();
        users = mFirestore.collection("Users");

    }


    public void login(View view) {
        String email = emailText.getText().toString();
        String passwordString = passwordText.getText().toString();

        if(email.equals("") || passwordString.equals("")){
            return;
        }

        mAuth.signInWithEmailAndPassword(email, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startShopping();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid email or password!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void register(View view) {
        Intent intent  = new Intent(this, RegisterActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    private void startShopping() {
        Intent intent = new Intent(this, ShopListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName", emailText.getText().toString());
        editor.putString("password", passwordText.getText().toString());
        editor.apply();

    }
}

