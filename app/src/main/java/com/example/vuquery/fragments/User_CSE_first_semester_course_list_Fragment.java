package com.example.vuquery.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.vuquery.R;
import com.example.vuquery.activities.UserCourseMaterialsListActivity;

public class User_CSE_first_semester_course_list_Fragment extends Fragment{

    CardView englishCard, mathCard, basicPhysicsCard, basicPhysicsLabCard, computerFundamentalCard, computerFundamentalLabCard;

    String courseName, courseId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_cse_first_semester_course_list, container, false);

        englishCard = view.findViewById(R.id.english_111_course_cardView);
        basicPhysicsCard = view.findViewById(R.id.physics_111_course_cardView);
        mathCard = view.findViewById(R.id.math_111_course_cardView);
        basicPhysicsLabCard = view.findViewById(R.id.physics_112_course_cardView);
        computerFundamentalCard = view.findViewById(R.id.cse_111_course_cardView);
        computerFundamentalLabCard = view.findViewById(R.id.cse_112_course_cardView);

        englishCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                courseName="English Fundamentals";
                Intent intent = new Intent(getActivity(), UserCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                courseId = "11";
                intent.putExtra("course id", courseId);
                startActivity(intent);
            }
        });
        mathCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                courseName="Differential and Integral Calculus";
                Intent intent = new Intent(getActivity(), UserCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                courseId = "12";
                intent.putExtra("course id", courseId);
                startActivity(intent);
            }
        });
        basicPhysicsCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                courseName="Basic Physics";
                Intent intent = new Intent(getActivity(), UserCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                courseId = "13";
                intent.putExtra("course id", courseId);
                startActivity(intent);
            }
        });
        basicPhysicsLabCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                courseName="Basic Physics Lab";
                Intent intent = new Intent(getActivity(), UserCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                courseId = "14";
                intent.putExtra("course id", courseId);
                startActivity(intent);
            }
        });
        computerFundamentalCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                courseName="Computer Fundamentals";
                Intent intent = new Intent(getActivity(), UserCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                courseId = "15";
                intent.putExtra("course id", courseId);
                startActivity(intent);
            }
        });
        computerFundamentalLabCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                courseName="Computer Application Lab";
                Intent intent = new Intent(getActivity(), UserCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                courseId = "16";
                intent.putExtra("course id", courseId);
                startActivity(intent);
            }
        });
        return view;
    }
}