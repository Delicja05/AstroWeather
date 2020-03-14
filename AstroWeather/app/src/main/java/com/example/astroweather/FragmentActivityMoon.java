package com.example.astroweather;

import android.annotation.SuppressLint;
import android.util.Log;
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

public class FragmentActivityMoon extends Fragment {

    private Double latitude;
    private Double longitude;
    private int timeRefresh;

    TextView textViewMoonriseTime;
    TextView textViewMoonSetTime;
    TextView textViewNewMoonDate;
    TextView textViewFullMoonDate;
    TextView textViewMoonPhasePercent;
    TextView textViewSynodMonthDay;

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
                                    Log.d("THREAD MOON", "REFRESH DATA");
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

        View view = inflater.inflate(R.layout.activity_fragment_moon, container, false);

        if(getArguments() != null){
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

        Log.d("FragmentActivityMoon", latitude + " " + longitude + " " + timeRefresh);

        textViewMoonriseTime = view.findViewById(R.id.TextViewMoonriseTime);
        textViewMoonSetTime = view.findViewById(R.id.TextViewMoonSetTime);
        textViewNewMoonDate = view.findViewById(R.id.TextViewNewMoonDate);
        textViewFullMoonDate = view.findViewById(R.id.TextViewFullMoonDate);
        textViewMoonPhasePercent = view.findViewById(R.id.TextViewMoonPhasePercent);
        textViewSynodMonthDay = view.findViewById(R.id.TextViewSynodMonthDay);

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
        String moonriseTime = astroCalculator.getMoonInfo().getMoonrise().getHour() + ":" + repairData(astroCalculator.getMoonInfo().getMoonrise().getMinute()) + ":" + repairData(astroCalculator.getMoonInfo().getMoonrise().getSecond());
        String moonSetTime = astroCalculator.getMoonInfo().getMoonset().getHour() + ":" + repairData(astroCalculator.getMoonInfo().getMoonset().getMinute()) + ":" + repairData(astroCalculator.getMoonInfo().getMoonset().getSecond());
        String newMoonDate = repairData(astroCalculator.getMoonInfo().getNextNewMoon().getDay()) + "-" + repairData(astroCalculator.getMoonInfo().getNextNewMoon().getMonth()) + "-" + astroCalculator.getMoonInfo().getNextNewMoon().getYear();
        String fullMoonDate = repairData(astroCalculator.getMoonInfo().getNextFullMoon().getDay()) + "-" + repairData(astroCalculator.getMoonInfo().getNextFullMoon().getMonth()) + "-" + astroCalculator.getMoonInfo().getNextFullMoon().getYear();
//        String moonPhasePercent = String.valueOf(astroCalculator.getMoonInfo().getIllumination() * 100);
        String moonPhasePercent = String.format("%.2f ", astroCalculator.getMoonInfo().getIllumination() * 100) + "%";
        String synodMonthDay;
        if((int) Math.round(astroCalculator.getMoonInfo().getAge())<0)
            synodMonthDay = ((int) Math.round(astroCalculator.getMoonInfo().getAge())) * (-1) + " day";
        else
            synodMonthDay = (int) Math.round(astroCalculator.getMoonInfo().getAge()) + " day";

        updateData(moonriseTime, moonSetTime, newMoonDate, fullMoonDate, moonPhasePercent, synodMonthDay);
    }

    @SuppressLint("SetTextI18n")
    void updateData(final String moonriseTime, final String moonSetTime, final String newMoonDate, final String fullMoonDate, final String moonPhasePercent, final String synodMonthDay){

        textViewMoonriseTime.setText("Time: " + moonriseTime);
        textViewMoonSetTime.setText("Time: " + moonSetTime);
        textViewNewMoonDate.setText("Date: " + newMoonDate);
        textViewFullMoonDate.setText("Date: " + fullMoonDate);
        textViewMoonPhasePercent.setText(moonPhasePercent);
        textViewSynodMonthDay.setText(synodMonthDay);
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
