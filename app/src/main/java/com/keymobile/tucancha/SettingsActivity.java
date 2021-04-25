package com.keymobile.tucancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    Button btnCanchas;
    Button btnPolideportivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnCanchas = findViewById(R.id.btnCanchas);
        btnPolideportivos = findViewById(R.id.btnPolideportivos);

        btnCanchas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MantCanchaActivity.class);
                startActivity(intent);
            }
        });

        btnPolideportivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MantPolideportivoActivity.class);
                startActivity(intent);
            }
        });


    }
}