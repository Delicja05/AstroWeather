package com.example.astroweather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

public class FragmentAdditionalData extends Fragment {

    TextView textViewWindSpeed;
    TextView textViewWindDirection;
    TextView textViewHumidity;
    TextView textViewVisibility;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_additional, container, false);

        textViewWindSpeed = view.findViewById(R.id.TextViewWindSpeedValue);
        textViewWindDirection = view.findViewById(R.id.TextViewWindDirectionValue);
        textViewHumidity = view.findViewById(R.id.TextViewHumidityValue);
        textViewVisibility = view.findViewById(R.id.TextViewVisibilityValue);

        update();

        return view;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void update() {
        try {
            ForecastModel forecastModel = ((MainForWeatherFragment) Objects.requireNonNull(getActivity())).forecastModel;

            textViewWindDirection.setText(forecastModel.current_observation.wind.direction+"°");
            textViewHumidity.setText(forecastModel.current_observation.atmosphere.humidity+"%");

            if(MainWeatherActivity.getUnit().equals("metric")){
                textViewWindSpeed.setText(forecastModel.current_observation.wind.speed + " km/h");
                textViewVisibility.setText(forecastModel.current_observation.atmosphere.visibility + " km");
            }else {
                textViewWindSpeed.setText((int)(1000*Double.parseDouble(forecastModel.current_observation.wind.speed)) + " m/h");
                textViewVisibility.setText((int)(1000*Double.parseDouble(forecastModel.current_observation.atmosphere.visibility))+" m");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
