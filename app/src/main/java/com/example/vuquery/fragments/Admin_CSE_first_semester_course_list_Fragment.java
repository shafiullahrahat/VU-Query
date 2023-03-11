package com.example.vuquery.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vuquery.R;
import com.example.vuquery.activities.AdminCourseMaterialsListActivity;
import com.example.vuquery.activities.UserCourseMaterialsListActivity;

public class Admin_CSE_first_semester_course_list_Fragment extends Fragment {

    CardView englishCard, mathCard, basicPhysicsCard, basicPhysicsLabCard, computerFundamentalCard, computerFundamentalLabCard;

    String courseName, courseId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_cse_first_semester_course_list, container, false);

        englishCard = view.findViewById(R.id.english_111_course_cardView);
        basicPhysicsCard = view.findViewById(R.id.physics_111_course_cardView);
        mathCard = view.findViewById(R.id.math_111_course_cardView);
        basicPhysicsLabCard = view.findViewById(R.id.physics_112_course_cardView);
        computerFundamentalCard = view.findViewById(R.id.cse_111_course_cardView);
        computerFundamentalLabCard = view.findViewById(R.id.cse_112_course_cardView);

        englishCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseName="English Fundamentals";
                courseId = "11";
                Intent intent = new Intent(getActivity(), AdminCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                intent.putExtra("course id", courseId);
                startActivity(intent);
            }
        });
        basicPhysicsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseName="Basic Physics";
                courseId = "13";
                Intent intent = new Intent(getActivity(), AdminCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                intent.putExtra("course id", courseId);
                startActivity(intent);
            }
        });
        mathCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseName="Differential and Integral Calculus";
                courseId = "12";
                Intent intent = new Intent(getActivity(), AdminCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                intent.putExtra("course id", courseId);
                startActivity(intent);
            }
        });
        basicPhysicsLabCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseName="Basic Physics Lab";
                courseId = "14";
                Intent intent = new Intent(getActivity(), AdminCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                intent.putExtra("course id", courseId);
                startActivity(intent);
            }
        });
        computerFundamentalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseName="Computer Fundamentals";
                courseId = "15";
                Intent intent = new Intent(getActivity(), AdminCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                intent.putExtra("course id", courseId);
                startActivity(intent);
            }
        });
        computerFundamentalLabCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseName="Computer Application Lab";
                courseId = "16";
                Intent intent = new Intent(getActivity(), AdminCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                intent.putExtra("course id", courseId);
                startActivity(intent);
            }
        });

        return view;
    }
}