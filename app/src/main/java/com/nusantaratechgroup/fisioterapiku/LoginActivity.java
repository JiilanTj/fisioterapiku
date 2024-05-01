package com.nusantaratechgroup.fisioterapiku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nusantaratechgroup.fisioterapiku.api.ApiClient;
import com.nusantaratechgroup.fisioterapiku.api.ApiInterface;
import com.nusantaratechgroup.fisioterapiku.model.LoginRequest;
import com.nusantaratechgroup.fisioterapiku.model.LoginResponse;
import com.nusantaratechgroup.fisioterapiku.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etemail);
        etPassword = findViewById(R.id.etpassword);

        findViewById(R.id.btnlogin).setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Mohon isi email dan password", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.userLogin(new LoginRequest(email, password));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    String message = loginResponse.getMessage();
                    if ("Login success".equals(message)) {
                        // Simpan token ke SessionManager
                        SessionManager.getInstance(LoginActivity.this).saveToken(loginResponse.getAccessToken());
                        // Pindah ke MainActivity
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Email/Password Salah", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Gagal melakukan login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
