package com.example.vuquery.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vuquery.R;
import com.example.vuquery.adapters.newAdapter;
import com.example.vuquery.classes.PdfModelClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserCourseMaterialsListActivity extends AppCompatActivity {

    TextView course_name;
    ImageButton backButton;
    RecyclerView pdfShowRecyclerView;

    String[] coursePdfNames;
    newAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_course_materials_list);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String courseName = intent.getStringExtra("course name");
        String courseId = intent.getStringExtra("course id");
        course_name = findViewById(R.id.course_names_textView);
        course_name.setText(courseName);

        pdfShowRecyclerView = findViewById(R.id.pdfList_recyclerView);
        if (courseId.equals("11")){
            coursePdfNames = getResources().getStringArray(R.array.EnglishFundamentals);
        }else if (courseId .equals("12")) {
            coursePdfNames = getResources().getStringArray(R.array.DifferentialandIntegralCalculus);
        }else if (courseId.equals("13")) {
            coursePdfNames = getResources().getStringArray(R.array.EnglishFundamentals);
        }else if (courseId.equals("14")) {
            coursePdfNames = getResources().getStringArray(R.array.EnglishFundamentals);
        }else if (courseId.equals("15")) {
            coursePdfNames = getResources().getStringArray(R.array.ComputerFundamentals);
        }else if (courseId.equals("16")) {
            coursePdfNames = getResources().getStringArray(R.array.ComputerFundamentals);
        }
        adapter = new newAdapter(this, coursePdfNames, courseName);
        pdfShowRecyclerView.setAdapter(adapter);
        pdfShowRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}