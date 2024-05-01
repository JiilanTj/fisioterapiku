package com.nusantaratechgroup.fisioterapiku;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.nusantaratechgroup.fisioterapiku.session.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = SessionManager.getInstance(this);

        if (sessionManager.getToken() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
        }
    }
}
