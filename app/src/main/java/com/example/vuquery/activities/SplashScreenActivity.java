package com.example.vuquery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuquery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private TextView slogan, credit;
    private ImageView logo;
    private View topView1, topView2, topView3;
    private View bottomView1, bottomView2, bottomView3;

    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_FULLSCREEN
        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        firebaseAuth = FirebaseAuth.getInstance();

        logo = findViewById(R.id.app_logo);
        slogan = findViewById(R.id.app_slogan);
        credit = findViewById(R.id.app_credit);

        topView1 = findViewById(R.id.topView1);
        topView2 = findViewById(R.id.topView2);
        topView3 = findViewById(R.id.topView3);

        bottomView1 = findViewById(R.id.bottomView1);
        bottomView2 = findViewById(R.id.bottomView2);
        bottomView3 = findViewById(R.id.bottomView3);

        Animation logoAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);
        Animation sloganAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);
        Animation creditAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);

        Animation topView1Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_view_animation);
        Animation topView2Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_view_animation);
        Animation topView3Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_view_animation);

        Animation bottomView1Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_view_animation);
        Animation bottomView2Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_view_animation);
        Animation bottomView3Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_view_animation);

        topView1.startAnimation(topView1Animation);
        bottomView1.startAnimation(bottomView1Animation);

        topView1Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                topView2.setVisibility(View.VISIBLE);
                bottomView2.setVisibility(View.VISIBLE);

                topView2.startAnimation(topView2Animation);
                bottomView2.startAnimation(bottomView2Animation);

                logo.setVisibility(View.VISIBLE);
                logo.startAnimation(logoAnimation);

                slogan.setVisibility(View.VISIBLE);
                slogan.startAnimation(sloganAnimation);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        topView2Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                topView3.setVisibility(View.VISIBLE);
                bottomView3.setVisibility(View.VISIBLE);

                topView3.startAnimation(topView3Animation);
                bottomView3.startAnimation(bottomView3Animation);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sloganAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        credit.setVisibility(View.VISIBLE);
                        final String animateText = credit.getText().toString();
                        credit.setText("");

                        new CountDownTimer(animateText.length()*100, 100){
                            @Override
                            public void onTick(long l) {
                                credit.setText(credit.getText().toString()+animateText.charAt(count));
                                count++;
                            }
                            @Override
                            public void onFinish() {
                            }
                        }.start();
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        },800);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
            }
        },3900);
    }

    private void checkUser() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null){
            //user not logged in
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }else{
            //user already logged in
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
            databaseReference.child(firebaseUser.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            String userType = ""+snapshot.child("UserType").getValue();

                            if (userType.equals("user")){
                                startActivity(new Intent(SplashScreenActivity.this, UserDashboardActivity.class));
                                finish();
                            }else if (userType.equals("admin")){
                                startActivity(new Intent(SplashScreenActivity.this, AdminDashboardActivity.class));
                                finish();
                            }else if (userType.equals("Main Admin")){
                                startActivity(new Intent(SplashScreenActivity.this, MainAdminDashboardActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
        }
    }
}