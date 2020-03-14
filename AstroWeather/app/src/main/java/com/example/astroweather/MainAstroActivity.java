package com.example.astroweather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class MainAstroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] EW = {"E", "W"};
    String[] NS = {"N", "S"};
    String[] time = {"5 sec", "15 sec", "30 sec"};

    String latitudeNS;
    String longitudeEW;
    Double latitude;
    Double longitude;
    int timeRefresh;

    EditText editTextLatitude;
    Spinner spinnerLatitude;
    EditText editTextLongitude;
    Spinner spinnerLongitude;
    Spinner spinnerTimeRefresh;
    Button but_next;

    Thread updateTime;
    SimpleDateFormat sdf;
    TextView textViewActualTime;

    @Override
    public void onStop() {
        super.onStop();
        if(updateTime != null)
            updateTime.interrupt();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(updateTime != null)
            updateTime.interrupt();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateTime = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("THREAD", "ACTUAL TIME FIRST");
                                textViewActualTime.setText(sdf.format(System.currentTimeMillis()));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        updateTime.start();
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_astro);

        editTextLatitude = findViewById(R.id.EditTextLatitude);
        spinnerLatitude = findViewById(R.id.SpinnerLatitude);
        editTextLongitude = findViewById(R.id.EditTextLongitude);
        spinnerLongitude = findViewById(R.id.SpinnerLongitude);
        spinnerTimeRefresh = findViewById(R.id.SpinnerTimeRefresh);
        but_next = findViewById(R.id.button_next);
        textViewActualTime = findViewById(R.id.TextViewActualTime1);

        sdf = new SimpleDateFormat("hh:mm:ss a");

        ArrayAdapter arrayLatitude = new ArrayAdapter(this, android.R.layout.simple_spinner_item, NS);
        arrayLatitude.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLatitude.setAdapter(arrayLatitude);

        ArrayAdapter arrayLongitude = new ArrayAdapter(this, android.R.layout.simple_spinner_item, EW);
        arrayLongitude.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLongitude.setAdapter(arrayLongitude);

        ArrayAdapter arrayTimeRefresh = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time);
        arrayTimeRefresh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeRefresh.setAdapter(arrayTimeRefresh);


        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_next:
                        if(String.valueOf(editTextLatitude.getText()).equals("") || String.valueOf(editTextLongitude.getText()).equals("")){
                            Toast.makeText(getBaseContext(), "Fill in the fields!", Toast.LENGTH_LONG).show();
                        }else {
                            latitude = Double.parseDouble(String.valueOf(editTextLatitude.getText()));
                            longitude = Double.parseDouble(String.valueOf(editTextLongitude.getText()));

                            if(latitude < 0 || latitude > 180 || longitude < 0 || longitude > 90){
                                Toast.makeText(getBaseContext(), "Incorrect data!", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Intent intent = new Intent(v.getContext(), MainForAstroFragment.class);
                                intent.putExtra("latitude", latitude);
                                intent.putExtra("latitudeNS", latitudeNS);
                                intent.putExtra("longitude", longitude);
                                intent.putExtra("longitudeEW", longitudeEW);
                                intent.putExtra("timeRefresh", timeRefresh);
                                startActivity(intent);
                            }
                        }
                        break;
                }
            }
        };

        but_next.setOnClickListener(handler);

        spinnerLatitude.setOnItemSelectedListener(this);
        spinnerLongitude.setOnItemSelectedListener(this);
        spinnerTimeRefresh.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        if(String.valueOf(arg0).contains("SpinnerLatitude")) {
            latitudeNS = NS[position];
        }
        else if (String.valueOf(arg0).contains("SpinnerLongitude")) {
            longitudeEW = EW[position];
        }
        else  if (String.valueOf(arg0).contains("SpinnerTimeRefresh")) {
            String str = time[position];
            timeRefresh = Integer.parseInt(str.substring(0,str.length()-4));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        if(String.valueOf(arg0).contains("SpinnerLatitude")) {
            latitudeNS = "N";
        }
        else if (String.valueOf(arg0).contains("SpinnerLongitude")) {
            longitudeEW = "E";
        }
        else  if (String.valueOf(arg0).contains("SpinnerTimeRefresh")) {
            timeRefresh = 5;
        }

    }

}
