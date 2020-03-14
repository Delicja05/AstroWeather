package com.example.astroweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MainWeatherActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    boolean valid;
    boolean finish;

    String[] units = {"imperial", "metric"};
    static String unit;

    Spinner spinner_units;
    Button button_addCity;
    EditText editTextCity;
    LinearLayout cityButtonList;
    String[] buttons = new String[80];
    String buttonsJsonString;

    ForecastModel forecastModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

        cityButtonList = findViewById(R.id.CityButtonList);

        readFromFavouriteMemory();

        editTextCity  = findViewById(R.id.EditTextCity);
        button_addCity  = findViewById(R.id.ButtonAddNewCity);

        button_addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validCityName(editTextCity.getText().toString());
            }
        });

        spinner_units = findViewById(R.id.SpinnerUnits);

        ArrayAdapter arrayUnits = new ArrayAdapter(this, android.R.layout.simple_spinner_item, units);
        arrayUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_units.setAdapter(arrayUnits);

        spinner_units.setOnItemSelectedListener(this);

    }
    public void printToast(){
        Toast.makeText(getApplicationContext(), "Localization not found.", Toast.LENGTH_LONG).show();
    }

    public void addButton(){
        final Button button = new Button(getApplicationContext());
        button.setText(editTextCity.getText());
        button.setBackgroundColor(Color.parseColor("#AAAAAA"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainWeatherActivity.this, MainForWeatherFragment.class);
                intent.putExtra("city",button.getText().toString().replaceAll("\\s+",""));
                startActivity(intent);
            }
        });
        saveToFavouriteMemory(button);
        cityButtonList.addView(button);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        if (String.valueOf(arg0).contains("SpinnerUnits")) {
            unit = units[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        if (String.valueOf(arg0).contains("SpinnerUnits")) {
            unit = "metric";
        }

    }

    private boolean validCityName(final String localizationData) {
        forecastModel = null;
        finish = false;
        try{
            SendRequest request = new SendRequest(Request.Method.GET, null, null, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                    Gson gson = gsonBuilder.create();
                    String jsonString = response.toString();
                    if(jsonString.contains("\"location\":{}")){
                        valid = false;
                    }
                    else{
                        valid = true;
                    }
                    forecastModel = gson.fromJson(jsonString, ForecastModel.class);
                    if(!valid)
                        printToast();
                    else
                        addButton();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    valid = false;
                }
            });

            RequestManager requestManager = RequestManager.getInstance(getApplicationContext());
            request.setCity(localizationData);
            requestManager.addToRequestQueue(request);
        }catch (Exception e)
        {
            if(!valid)
                Toast.makeText(getApplicationContext(), "Localization not found.", Toast.LENGTH_LONG).show();
        }

        return valid;
    }

    private void saveToFavouriteMemory(Button button) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        Gson gson = gsonBuilder.create();
        for(int i = 0; i < buttons.length; i++)
            if(buttons[i] == null || buttons[i].isEmpty()) {
                buttons[i] = button.getText().toString();
                break;
            }
        buttonsJsonString = gson.toJson(buttons);
        SharedPreferences sharedPreferences = getSharedPreferences("buttonsJsonString", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("buttonsJsonString", buttonsJsonString);
        editor.commit();
    }

    private void readFromFavouriteMemory(){
        SharedPreferences sharedPreferences = getSharedPreferences("buttonsJsonString", MODE_PRIVATE);
        buttonsJsonString = sharedPreferences.getString("buttonsJsonString","");
        if(!buttonsJsonString.isEmpty()) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            buttons = gson.fromJson(buttonsJsonString,String[].class);
        }

        for(int i=0;i<buttons.length;i++) {
            if (buttons[i] != null && !buttons[i].isEmpty()) {
                final Button button = new Button(getApplicationContext());
                button.setText(buttons[i]);
                button.setBackgroundColor(Color.parseColor("#AAAAAA"));
                button.setPadding(0,10,0,10);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainWeatherActivity.this, MainForWeatherFragment.class);
                        intent.putExtra("city",button.getText().toString().replaceAll("\\s+",""));
                        startActivity(intent);
                    }
                });
                cityButtonList.addView(button);
            }
            else
                break;
        }
    }

    public static String getUnit() {
        return unit;
    }

}
