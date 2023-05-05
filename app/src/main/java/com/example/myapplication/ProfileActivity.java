package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private CollectionReference users;
    Button editPhoneButton;
    Button editAddressButton;
    TextView nameTextView;
    TextView emailTextView;
    TextView phoneTextView;
    TextView addressTextView;
    FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editPhoneButton = findViewById(R.id.edit_phone_button);
        editAddressButton = findViewById(R.id.edit_address_button);

        mFirestore = FirebaseFirestore.getInstance();

        TextView nameTextView = findViewById(R.id.name_value_text_view);
        TextView emailTextView = findViewById(R.id.email_value_text_view);
        TextView phoneTextView = findViewById(R.id.phone_value_text_view);
        TextView addressTextView = findViewById(R.id.address_value_text_view);


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(uid);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    String name = documentSnapshot.getString("userName");
                    String email = documentSnapshot.getString("email");
                    String phone = documentSnapshot.getString("phone");
                    String address = documentSnapshot.getString("address");


                    nameTextView.setText(name);
                    emailTextView.setText(email);
                    phoneTextView.setText(phone);
                    addressTextView.setText(address);
                }
            }
        });

        editPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(ProfileActivity.this);
                dialog.setContentView(R.layout.edit_phone_dialog);
                dialog.setTitle("Edit Phone");


                TextView titleTextView = dialog.findViewById(R.id.title_text_view);
                final EditText dataEditText = dialog.findViewById(R.id.data_edit_text);
                Button cancelButton = dialog.findViewById(R.id.cancel_button);
                Button saveButton = dialog.findViewById(R.id.save_button);


                titleTextView.setText("Edit Phone");
                dataEditText.setHint("Enter your phone number");


                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String newData = dataEditText.getText().toString().trim();

                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


                        DocumentReference userRef = mFirestore.collection("Users").document(uid);
                        userRef.update("phone", newData);


                        phoneTextView.setText(newData);


                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });


        editAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(ProfileActivity.this);
                dialog.setContentView(R.layout.edit_address_button);
                dialog.setTitle("Edit address");


                TextView titleTextView = dialog.findViewById(R.id.title_adress_view);
                final EditText dataEditText = dialog.findViewById(R.id.address_edit_text);
                Button cancelButton = dialog.findViewById(R.id.cancel_button);
                Button saveButton = dialog.findViewById(R.id.save_button);


                titleTextView.setText("Edit address");
                dataEditText.setHint("Enter your address");


                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String newData = dataEditText.getText().toString().trim();

                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


                        DocumentReference userRef = mFirestore.collection("Users").document(uid);
                        userRef.update("address", newData);


                        addressTextView.setText(newData);


                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });
    }

    public void cancelPage(View view) {
        finish();
    }

}