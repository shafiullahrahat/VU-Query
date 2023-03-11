package com.example.vuquery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.vuquery.R;

public class webview_Activity extends AppCompatActivity {

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Intent intent = getIntent();
        String menuItemNo = intent.getStringExtra("menuItemNo");

        webview = findViewById(R.id.teacher_details_webView);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());

        if (menuItemNo.equals("userTeacherDetails") || menuItemNo.equals("adminTeacherDetails")){
            webview.loadUrl("https://vu.edu.bd/academics/departments/computer-science-and-engineering/faculty-members");
        }else if(menuItemNo.equals("userVarsityWebsite") || menuItemNo.equals("adminVarsityWebsite")){
            webview.loadUrl("https://vu.edu.bd/");
        }else if (menuItemNo.equals("adminPrivacy") || menuItemNo.equals("userPrivacy")){
            webview.loadUrl("file:///android_asset/privacy_policy.html");
        }
    }
}