package com.gaurab.todoappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.text_username_id);
        password = (EditText) findViewById(R.id.text_password_id);
        btnLogin = findViewById(R.id.login_activity_btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().trim().toLowerCase().equals("user") && password.getText().toString().trim().toLowerCase().equals("user")) {
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("todo pref", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("authentication", true);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if(userName.getText().toString().equals("")){
                        userName.requestFocus();
                        userName.setError("Name is required");
                        return;
                    }
                    if(password.getText().toString().equals("")){
                        password.requestFocus();
                        password.setError("Password is required");
                        return;
                    }
                }
            }
        });
    }
}