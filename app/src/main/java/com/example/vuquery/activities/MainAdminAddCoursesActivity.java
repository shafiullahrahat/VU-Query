package com.example.vuquery.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.vuquery.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainAdminAddCoursesActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    ImageButton backButton;
    Button submitButton;
    EditText courseNameEditText;

    private String courseName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_add_course);

        firebaseAuth = FirebaseAuth.getInstance();

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait Your Honour");
        progressDialog.setCanceledOnTouchOutside(false);

        submitButton = findViewById(R.id.submit_course);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationOfData();
            }
        });

    }

    private void validationOfData() {

        courseNameEditText = findViewById(R.id.course_name_editText);
        courseName = courseNameEditText.getText().toString().trim();

        if (TextUtils.isEmpty(courseName)){
            Toast.makeText(getApplicationContext(), "Course Name Required", Toast.LENGTH_SHORT).show();
        }
        else{
            addCourseToFirebase();
        }
    }

    private void addCourseToFirebase() {

        progressDialog.setMessage("Adding Course...");
        progressDialog.show();

        long timestamp = System.currentTimeMillis();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", ""+timestamp);
        hashMap.put("Course Name", courseName);
        hashMap.put("timestamp", timestamp);
        hashMap.put("Uid", ""+firebaseAuth.getUid());

        databaseReference = FirebaseDatabase.getInstance().getReference("CSE Department Courses");
        databaseReference.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Course added Successfully", Toast.LENGTH_SHORT).show();
                        courseNameEditText.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}