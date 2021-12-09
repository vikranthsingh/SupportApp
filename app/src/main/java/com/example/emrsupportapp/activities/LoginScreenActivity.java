package com.example.emrsupportapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginScreenActivity extends AppCompatActivity {

    @BindView(R.id.etTxtUsername)
    public TextInputEditText etTxtUsername;

    @BindView(R.id.etTxtPassword)
    public TextInputEditText etTxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    void buttonClicked() {
        String username = etTxtUsername.getText().toString();
        String password = etTxtPassword.getText().toString();
        if (!username.isEmpty() && !password.isEmpty()) {
            if (username.equals("user") && password.equals("pass")) {
                startActivity(new Intent(LoginScreenActivity.this, DashboardActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please enter Username and Password", Toast.LENGTH_SHORT).show();
        }
        SharedPreferences sharedPreferences = getSharedPreferences("shared_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usernameKey", username);
        editor.putString("passwordKey", password);
        editor.commit();
    }
}