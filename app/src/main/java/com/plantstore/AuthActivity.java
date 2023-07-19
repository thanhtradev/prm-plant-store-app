package com.plantstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.plantstore.models.api.UserResponse;
import com.plantstore.ultils.SharedPrefUtils;

public class AuthActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        // On load, check if the user is already logged in
        // If the user is already logged in, navigate to the home screen
        UserResponse userResponse = SharedPrefUtils.getSavedUserResponse(this);
        if (userResponse != null) {
            startActivity(new Intent(AuthActivity.this, HomeActivity.class));
            finish();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login button click
                startActivity(new Intent(AuthActivity.this, LoginActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle register button click
                startActivity(new Intent(AuthActivity.this, RegisterActivity.class));
            }
        });
    }
}