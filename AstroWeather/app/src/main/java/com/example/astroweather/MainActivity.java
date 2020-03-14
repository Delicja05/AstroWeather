package com.example.astroweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    Button button_weather;
    Button button_astro;
    Button but_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_weather = findViewById(R.id.button_weather);
        button_astro = findViewById(R.id.button_astro);
        but_exit = findViewById(R.id.button_exit);

        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_weather:
                        startActivity(new Intent(v.getContext(), MainWeatherActivity.class));
                        break;
                    case R.id.button_astro:
                        startActivity(new Intent(v.getContext(), MainAstroActivity.class));
                        break;
                    case R.id.button_exit:
                        finish();
                        System.exit(0);
                        break;
                }
            }
        };

        button_weather.setOnClickListener(handler);
        button_astro.setOnClickListener(handler);
        but_exit.setOnClickListener(handler);

    }
}
