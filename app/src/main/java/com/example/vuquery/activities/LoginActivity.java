package com.example.vuquery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vuquery.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private TextView emailTv, stdIdTv, passTv;

    Button loginButton;
    TextView signupTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        signupTV = findViewById(R.id.signup_textView);
        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationOfData();
            }
        });
    }

    private String email = "", stdId = "", password = "";
    private void validationOfData() {
        emailTv = findViewById(R.id.login_email_editText);
        stdIdTv = findViewById(R.id.login_student_id_editText);
        passTv = findViewById(R.id.login_password_editText);

        email = emailTv.getText().toString().trim();
        stdId = stdIdTv.getText().toString().trim();
        password = passTv.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Need Valid Email", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(stdId)){
            Toast.makeText(getApplicationContext(), "Enter Your Id..", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Enter Password..", Toast.LENGTH_SHORT).show();
        }else{
            loginToUserAccount();
        }
    }

    private void loginToUserAccount() {

        progressDialog.setMessage("Checking User....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        checkUserAccount();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkUserAccount() {

        progressDialog.setMessage("Logging in..");
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        progressDialog.dismiss();
                        String userType = ""+snapshot.child("UserType").getValue();

                        if (userType.equals("user")){
                            startActivity(new Intent(LoginActivity.this, UserDashboardActivity.class));
                            finish();
                        }else if (userType.equals("admin")){
                            startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
                            finish();
                        }else if (userType.equals("Main Admin")){
                            startActivity(new Intent(LoginActivity.this, MainAdminDashboardActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }
}