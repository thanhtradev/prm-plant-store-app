package com.plantstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.plantstore.models.api.UserResponse;
import com.plantstore.models.api.RegisterRequest;

import com.plantstore.network.RetrofitClient;
import com.plantstore.services.UserApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnRegister;

    private UserApiService userApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userApiService = RetrofitClient.getClient().create(UserApiService.class);
        setContentView(R.layout.activity_register);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Validate username and password
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Perform registration logic here
                    // You can call a registration API or save the data to a database
                    RegisterRequest userRequest = new RegisterRequest(username, password);

                    Call<UserResponse> registerCall = userApiService.register(userRequest);
                    registerCall.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if (response.isSuccessful()) {
                                // Registration successful
                                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                // Finish the activity and navigate back to the login screen
                                finish();
                            } else {
                                // Registration failed
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            // Registration failed
                            Toast.makeText(RegisterActivity.this, "Registration failed here", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });
    }

}