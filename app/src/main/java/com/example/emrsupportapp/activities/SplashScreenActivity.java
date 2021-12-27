package com.example.emrsupportapp.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogged();
    }

    void isLogged() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_pref", MODE_PRIVATE);
        String username = sharedPreferences.getString("usernameKey", "");
        String password = sharedPreferences.getString("passwordKey", "");
        if (!username.isEmpty() && !password.isEmpty()) {
            startActivity(new Intent(SplashScreenActivity.this, DashboardActivity.class));
        } else {
            startActivity(new Intent(SplashScreenActivity.this, LoginScreenActivity.class));
        }
    }
}