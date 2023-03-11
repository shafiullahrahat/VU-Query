package com.example.vuquery.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vuquery.R;
import com.example.vuquery.fragments.CSE_eighth_semester_Fragment;
import com.example.vuquery.fragments.CSE_eleventh_semester_Fragment;
import com.example.vuquery.fragments.CSE_fifth_semester_Fragment;
import com.example.vuquery.fragments.User_CSE_first_semester_course_list_Fragment;
import com.example.vuquery.fragments.CSE_fourth_semester_Fragment;
import com.example.vuquery.fragments.CSE_nineth_semester_Fragment;
import com.example.vuquery.fragments.User_CSE_second_semester_course_list_Fragment;
import com.example.vuquery.fragments.CSE_seventh_semester_Fragment;
import com.example.vuquery.fragments.CSE_sixth_semester_Fragment;
import com.example.vuquery.fragments.CSE_tenth_semester_Fragment;
import com.example.vuquery.fragments.User_CSE_third_semester_course_list_Fragment;
import com.example.vuquery.fragments.CSE_twelveth_semester_Fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserDashboardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth firebaseAuth;
    ImageButton logOutBtn;
    TextView emailTv;
    Spinner dept_spinner, semester_spinner;
    Toolbar toolbar;

    String[] semester;

    DrawerLayout fullScreenLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        logOutBtn = findViewById(R.id.user_logout_button);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });

        String[] department = {"CSE","BBA","English","Pharmacy","LAW","EEE","Sociology","Economics"};
        dept_spinner = findViewById(R.id.dept_selection_dropdown_menu);
        dept_spinner.setOnItemSelectedListener(this);
        ArrayAdapter dept_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, department);
        dept_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept_spinner.setAdapter(dept_adapter);

        semester = new String[] {"1st","2nd","3rd","4th","5th","6th","7th","8th","9th","10th","11th","12th"};
        semester_spinner = findViewById(R.id.semester_selection_dropdown_menu);
        semester_spinner.setOnItemSelectedListener(this);
        ArrayAdapter semester_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, semester);
        semester_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester_spinner.setAdapter(semester_adapter);

        fullScreenLayout = findViewById(R.id.ful_screen_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,fullScreenLayout,R.string.nav_open,R.string.nav_close);
        fullScreenLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home_menu:
                        startActivity(new Intent(getApplicationContext(), UserDashboardActivity.class));
                        break;

                    case R.id.teacher_details:
                        Intent intent1 = new Intent(getApplicationContext(), webview_Activity.class);
                        intent1.putExtra("menuItemNo", "userTeacherDetails");
                        startActivity(intent1);
                        break;

                    case R.id.varsity_website:
                        Intent intent2 = new Intent(getApplicationContext(), webview_Activity.class);
                        intent2.putExtra("menuItemNo", "userVarsityWebsite");
                        startActivity(intent2);
                        break;

                    case R.id.shareMenu:
                        Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Download VuQuery App");
                        String app_url = " https://play.google.com/store/apps/details?id="+getPackageName();
                        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                        startActivity(Intent.createChooser(shareIntent, "Share via"));
                        break;

                    case R.id.privacyPolicyMenu:
                        Intent intent3 = new Intent(getApplicationContext(), webview_Activity.class);
                        intent3.putExtra("menuItemNo", "userPrivacy");
                        startActivity(intent3);
                        break;

                }
                return true;
            }
        });
    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser==null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }else{
            String email = firebaseUser.getEmail();
            emailTv = findViewById(R.id.user_email_textView);
            emailTv.setText(email);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        showCourseNames(i);
    }

    private void showCourseNames(int i) {
        if (i==0){
            Fragment fragment = new User_CSE_first_semester_course_list_Fragment();
            getSupportFragmentManager().beginTransaction().add(R.id.course_name_containing_relativeLayout, fragment).commit();
        }else if (i==1){
            Fragment fragment = new User_CSE_second_semester_course_list_Fragment();
            getSupportFragmentManager().beginTransaction().add(R.id.course_name_containing_relativeLayout, fragment).commit();
        }else if (i==2){
            Fragment fragment = new User_CSE_third_semester_course_list_Fragment();
            getSupportFragmentManager().beginTransaction().add(R.id.course_name_containing_relativeLayout, fragment).commit();
        }else if (i==3){
            Fragment fragment = new CSE_fourth_semester_Fragment();
            getSupportFragmentManager().beginTransaction().add(R.id.course_name_containing_relativeLayout, fragment).commit();
        }else if (i==4){
            Fragment fragment = new CSE_fifth_semester_Fragment();
            getSupportFragmentManager().beginTransaction().add(R.id.course_name_containing_relativeLayout, fragment).commit();
        }else if (i==5){
            Fragment fragment = new CSE_sixth_semester_Fragment();
            getSupportFragmentManager().beginTransaction().add(R.id.course_name_containing_relativeLayout, fragment).commit();
        }else if (i==6){
            Fragment fragment = new CSE_seventh_semester_Fragment();
            getSupportFragmentManager().beginTransaction().add(R.id.course_name_containing_relativeLayout, fragment).commit();
        }else if (i==7){
            Fragment fragment = new CSE_eighth_semester_Fragment();
            getSupportFragmentManager().beginTransaction().add(R.id.course_name_containing_relativeLayout, fragment).commit();
        }else if (i==8){
            Fragment fragment = new CSE_nineth_semester_Fragment();
            getSupportFragmentManager().beginTransaction().add(R.id.course_name_containing_relativeLayout, fragment).commit();
        }else if (i==9){
            Fragment fragment = new CSE_tenth_semester_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.course_name_containing_relativeLayout, fragment).commit();
        }else if (i==10){
            Fragment fragment = new CSE_eleventh_semester_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.course_name_containing_relativeLayout, fragment).commit();
        }else if (i==11){
            Fragment fragment = new CSE_twelveth_semester_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.course_name_containing_relativeLayout, fragment).commit();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}