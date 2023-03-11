package com.example.vuquery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vuquery.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    Button signupButton;
    ImageButton backButton;
    TextView loginTV;

    EditText stdName, stdID, password, cPassword, sEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        loginTV = findViewById(R.id.login_textView);
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        signupButton = findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationOfData();
            }
        });


    }

    private String name = "", email = "", pass = "", id = "", cPass = "";

    private void validationOfData() {
        stdName = findViewById(R.id.signup_student_name_editText);
        stdID = findViewById(R.id.signup_student_id_editText);
        sEmail = findViewById(R.id.signup_email_editText);
        password = findViewById(R.id.signup_password_editText);
        cPassword = findViewById(R.id.signup_confirm_password_editText);

        name = stdName.getText().toString().trim();
        email = sEmail.getText().toString().trim();
        id = stdID.getText().toString().trim();
        pass = password.getText().toString().trim();
        cPass = cPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(getApplicationContext(), "Name Required", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Need Valid Email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(id)){
            Toast.makeText(getApplicationContext(), "Id Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pass)){
            Toast.makeText(getApplicationContext(), "Password Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cPass)){
            Toast.makeText(getApplicationContext(), "Please Confirm Your Password", Toast.LENGTH_SHORT).show();
        }
        else if (!pass.equals(cPass)){
            Toast.makeText(getApplicationContext(), "Password didn't match", Toast.LENGTH_SHORT).show();
        }
        else{
            createUserAccount();
        }
    }

    private void createUserAccount() {

        progressDialog.setMessage("Signing You UP..");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                updateUserAccount1();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                        });
    }

    private void updateUserAccount1() {
        progressDialog.setMessage("Saving Information...");

        String uid = firebaseAuth.getUid();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Id", id);
        hashMap.put("Name", name);
        hashMap.put("Email", email);
        hashMap.put("Password", pass);
        hashMap.put("ProfileImage", "");
        hashMap.put("UserType", "user");

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Users");
        dbref.child(uid)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(SignUpActivity.this, UserDashboardActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}