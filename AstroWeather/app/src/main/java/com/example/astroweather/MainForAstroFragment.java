package com.example.astroweather;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainForAstroFragment extends AppCompatActivity  {

    Double latitude;
    String latitudeNS;
    Double longitude;
    String longitudeEW;
    int timeRefresh;

    TextView textViewFillData;
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
                                Log.d("THREAD", "ACTUAL TIME SECOND");
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        latitude = getIntent().getDoubleExtra("latitude",181);
        latitudeNS = getIntent().getStringExtra("latitudeNS");
        longitude = getIntent().getDoubleExtra("longitude", 181);
        longitudeEW = getIntent().getStringExtra("longitudeEW");
        timeRefresh = getIntent().getIntExtra("timeRefresh", 5);

        if(latitudeNS.equals("S")){
            latitude = latitude * (-1);
        }
        if(longitudeEW.equals("W")){
            longitude = longitude * (-1);
        }

        setContentView(R.layout.activity_main_view_pager_astro);

        Log.d("MainViewPager:",latitude + latitudeNS + " " + longitude + longitudeEW + " " + timeRefresh);

        textViewFillData = findViewById(R.id.TextViewFillData);
        textViewFillData.setText(latitude + latitudeNS + " " + longitude + longitudeEW);
        textViewActualTime = findViewById(R.id.TextViewActualTime2);

        sdf = new SimpleDateFormat("hh:mm:ss a");

        Log.d("MainViewPager:",latitude + " " + longitude);

        setViewAdapter();

    }

    void setViewAdapter(){

        int screensize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        int orientation = getResources().getConfiguration().orientation;

        if((orientation == Configuration.ORIENTATION_PORTRAIT && screensize == Configuration.SCREENLAYOUT_SIZE_NORMAL)
                || (orientation == Configuration.ORIENTATION_LANDSCAPE && screensize == Configuration.SCREENLAYOUT_SIZE_NORMAL)){

            ViewPager viewPager = findViewById(R.id.viewPager);
            if (viewPager != null) {
                ViewPagerAdapterAstro adapter = new ViewPagerAdapterAstro(getSupportFragmentManager(), latitude, longitude, timeRefresh);
                viewPager.setAdapter(adapter);
            }

        }
        else {
            Log.d("ViewPagerAdapterAstro",latitude + " " + longitude + " " + timeRefresh);
            FragmentActivitySun fragmentActivitySun = new FragmentActivitySun();
            FragmentActivityMoon fragmentActivityMoon = new FragmentActivityMoon();
            Log.d("ViewPagerAdapterAstro",latitude + " " + longitude + " " + timeRefresh);
        }

    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public int getTimeRefresh() {
        return timeRefresh;
    }
}