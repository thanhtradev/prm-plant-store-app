package com.plantstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.plantstore.models.api.LoginRequest;
import com.plantstore.models.api.UserResponse;
import com.plantstore.network.RetrofitClient;
import com.plantstore.services.UserApiService;
import com.plantstore.ultils.SharedPrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnLogin;
    private UserApiService userApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userApiService = RetrofitClient.getClient().create(UserApiService.class);
        setContentView(R.layout.activity_login);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                LoginRequest loginRequest = new LoginRequest(username, password);
                // Call the login API
                Call<UserResponse> loginCall = userApiService.login(loginRequest);
                loginCall.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()) {
                            UserResponse userResponse = response.body();
                            SharedPrefUtils.saveUserResponseLocally(LoginActivity.this, userResponse);
                            // Proceed to the next screen or perform the desired action
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            // Handle API error response
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        // Handle API call failure
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}