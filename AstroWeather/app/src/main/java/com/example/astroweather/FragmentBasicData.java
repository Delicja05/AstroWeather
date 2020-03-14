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

public class FragmentBasicData extends Fragment {

    TextView textViewCity;
    TextView textViewLatitude;
    TextView textViewLongitude;
    TextView textViewTemperature;
    TextView textViewPressure;
    TextView textViewDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_basic, container, false);

        textViewCity = view.findViewById(R.id.TextViewCityName);
        textViewLatitude = view.findViewById(R.id.TextViewLatitudeValue);
        textViewLongitude = view.findViewById(R.id.TextViewLongitudeValue);
        textViewTemperature = view.findViewById(R.id.TextViewTemperatureValue);
        textViewPressure = view.findViewById(R.id.TextViewPressureValue);
        textViewDescription = view.findViewById(R.id.TextViewDescriptionText);

        update();

        return view;
    }

    @SuppressLint("SetTextI18n")
    public void update() {
        try {
            ForecastModel forecastModel = ((MainForWeatherFragment) Objects.requireNonNull(getActivity())).forecastModel;

            textViewCity.setText(forecastModel.location.city);
            textViewLatitude.setText(forecastModel.location.latitude + "°");
            textViewLongitude.setText(forecastModel.location.longitude + "°");

            if(MainWeatherActivity.getUnit().equals("metric")){
                textViewTemperature.setText(forecastModel.current_observation.condition.temperature + " C");
                textViewPressure.setText(forecastModel.current_observation.atmosphere.pressure + " mbar");
            }else {
                textViewTemperature.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.current_observation.condition.temperature)))+" F");
                textViewPressure.setText((int)(0.03386 * (Double.parseDouble(forecastModel.current_observation.atmosphere.pressure))) + " inchHg");
            }

            textViewDescription.setText(forecastModel.current_observation.condition.text);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
