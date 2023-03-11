package com.example.vuquery.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vuquery.R;
import com.example.vuquery.activities.UserCourseMaterialsListActivity;

public class User_CSE_second_semester_course_list_Fragment extends Fragment {

    CardView mathCard, electricalCircuitCard, electricalCircuitLabCard, programmingCard, programmingLabCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_cse_second_semester_course_list, container, false);

        mathCard = view.findViewById(R.id.math_121_course_cardView);
        electricalCircuitCard = view.findViewById(R.id.cse_121_course_cardView);
        electricalCircuitLabCard = view.findViewById(R.id.cse_122_course_cardView);
        programmingCard = view.findViewById(R.id.cse_123_course_cardView);
        programmingLabCard = view.findViewById(R.id.cse_124_course_cardView);

        mathCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseName="Coordinate Geometry and Vector Analysis";
                Intent intent = new Intent(getActivity(), UserCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                startActivity(intent);
            }
        });

        electricalCircuitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseName="Electrical Circuits";
                Intent intent = new Intent(getActivity(), UserCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                startActivity(intent);
            }
        });

        electricalCircuitLabCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseName="Electrical Circuits Lab";
                Intent intent = new Intent(getActivity(), UserCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                startActivity(intent);
            }
        });

        programmingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseName="Structured Programming Language";
                Intent intent = new Intent(getActivity(), UserCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                startActivity(intent);
            }
        });

        programmingLabCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseName="Structured Programming Language Lab";
                Intent intent = new Intent(getActivity(), UserCourseMaterialsListActivity.class);
                intent.putExtra("course name", courseName);
                startActivity(intent);
            }
        });
        return view;
    }
}