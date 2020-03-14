package com.example.astroweather;

import android.util.Log;
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.util.Calendar;

public class FragmentActivitySun extends Fragment {

    private Double latitude;
    private Double longitude;
    private int timeRefresh;

    TextView textViewSunriseTime;
    TextView textViewSunriseAzimuth;
    TextView textViewSunsetTime;
    TextView textViewSunsetAzimuth;
    TextView textViewTwilightEveningTime;
    TextView textViewTwilightMorningTime;

    Thread refreshData;

    Calendar calendar;
    AstroDateTime astroDateTime;
    AstroCalculator.Location location;
    AstroCalculator astroCalculator;

    @Override
    public void onStop() {
        super.onStop();
        refreshData.interrupt();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(latitude != null && longitude != null) {
            refreshData = new Thread() {
                @Override
                public void run() {
                    try {
                        while (!isInterrupted()) {
                            Thread.sleep(timeRefresh * 1000);
                            if (getActivity() == null)
                                return;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("THREAD SUN", "REFRESH DATA");
                                    getData();
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();}
                }
            };
            refreshData.start();

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_sun, container, false);

        if (getArguments() != null) {
            setLatitude(getArguments().getDouble("latitude"));
            setLongitude(getArguments().getDouble("longitude"));
            setTimeRefresh(getArguments().getInt("timerefresh"));
        }
        else {
            MainForAstroFragment activity = (MainForAstroFragment) getActivity();
            setLatitude(activity.getLatitude());
            setLongitude(activity.getLongitude());
            setTimeRefresh(activity.getTimeRefresh());
        }

        Log.d("FragmentActivitySun:",latitude + " " + longitude + " " + timeRefresh);

        textViewSunriseTime = view.findViewById(R.id.TextViewSunriseTime);
        textViewSunriseAzimuth = view.findViewById(R.id.TextViewSunriseAzimuth);
        textViewSunsetTime = view.findViewById(R.id.TextViewSunsetTime);
        textViewSunsetAzimuth = view.findViewById(R.id.TextViewSunsetAzimuth);
        textViewTwilightEveningTime = view.findViewById(R.id.TextViewTwilightEveningTime);
        textViewTwilightMorningTime = view.findViewById(R.id.TextViewTwilightMorningTime);

        if(latitude != null && longitude != null){
            getData();
        }

        return view;
    }

    @SuppressLint("DefaultLocale")
    void getData(){
        calendar = Calendar.getInstance();
        astroDateTime = new AstroDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), 1, true);
        location = new AstroCalculator.Location(latitude,longitude);
        astroCalculator = new AstroCalculator(astroDateTime, location);
        String sunriseTime = astroCalculator.getSunInfo().getSunrise().getHour() + ":" + repairData(astroCalculator.getSunInfo().getSunrise().getMinute()) + ":" + repairData(astroCalculator.getSunInfo().getSunrise().getSecond());
        String sunriseAzimuth = String.format("%.2f°", astroCalculator.getSunInfo().getAzimuthRise());
        String sunsetTime = astroCalculator.getSunInfo().getSunset().getHour() + ":" + repairData(astroCalculator.getSunInfo().getSunset().getMinute()) + ":" + repairData(astroCalculator.getSunInfo().getSunset().getSecond());
        String sunsetAzimuth = String.format("%.2f°", astroCalculator.getSunInfo().getAzimuthSet());
        String twilightEveningTime = astroCalculator.getSunInfo().getTwilightEvening().getHour() + ":" + repairData(astroCalculator.getSunInfo().getTwilightEvening().getMinute()) + ":" + repairData(astroCalculator.getSunInfo().getTwilightEvening().getSecond());
        String twilightMorningTime = astroCalculator.getSunInfo().getTwilightMorning().getHour() + ":" + repairData(astroCalculator.getSunInfo().getTwilightMorning().getMinute()) + ":" + repairData(astroCalculator.getSunInfo().getTwilightMorning().getSecond());

        updateData(sunriseTime, sunriseAzimuth, sunsetTime, sunsetAzimuth, twilightEveningTime, twilightMorningTime);
    }

    @SuppressLint("SetTextI18n")
    void updateData(final String sunriseTime, final String sunriseAzimuth, final String sunsetTime, final String sunsetAzimuth, final String twilightEveningTime, final String twilightMorningTime){
        textViewSunriseTime.setText("Time: " + sunriseTime);
        textViewSunriseAzimuth.setText("Azimuth: " + sunriseAzimuth);
        textViewSunsetTime.setText("Time: " + sunsetTime);
        textViewSunsetAzimuth.setText("Azimuth: " + sunsetAzimuth);
        textViewTwilightEveningTime.setText("Time: " + twilightEveningTime);
        textViewTwilightMorningTime.setText("Time: " + twilightMorningTime);

    }

    String repairData(int a){
        if(a < 10){
            return "0" + a;
        }
        else
            return String.valueOf(a);
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setTimeRefresh(int timeRefresh) {
        this.timeRefresh = timeRefresh;
    }

}
