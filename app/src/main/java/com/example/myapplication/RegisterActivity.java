package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 42;


    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordEditText;
    EditText passwordConfirmEditText;
    EditText phoneEditText;
    EditText addressEditText;


    private SharedPreferences preferences;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private CollectionReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RelativeLayout rootLayout = findViewById(R.id.root_layout);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        rootLayout.startAnimation(anim);

        mAuth = FirebaseAuth.getInstance();

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secret_key != 42) {
            finish();
        }

        userNameEditText = findViewById(R.id.userNameEditText);
        userEmailEditText = findViewById(R.id.userEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordConfirmEditText = findViewById(R.id.passwordAgainEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        addressEditText = findViewById(R.id.addressEditText);


        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String email = preferences.getString("userName", "");
        String password = preferences.getString("password", "");

        userEmailEditText.setText(email);
        passwordEditText.setText(password);
        passwordConfirmEditText.setText(password);

        mFirestore = FirebaseFirestore.getInstance();
        users = mFirestore.collection("Users");

    }

    public void register(View view) {
        String userName = userNameEditText.getText().toString();
        String email = userEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordConfirm = passwordConfirmEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String address = addressEditText.getText().toString();

        if(userName.equals("") || email.equals("") || password.equals("") || passwordConfirm.equals("") || phone.equals("") || address.equals("")){
            return;
        }


        if (!password.equals(passwordConfirm)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


                    DocumentReference userRef = mFirestore.collection("Users").document(uid);


                    userRef.set(new User(userName, email, phone, address));


                    startShopping();
                } else {
                    Toast.makeText(RegisterActivity.this, "User was't created successfully:", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancel(View view) {
        finish();
    }

    private void startShopping() {
        Intent intent = new Intent(this, ShopListActivity.class);
        startActivity(intent);
    }
}