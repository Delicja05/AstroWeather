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

public class FragmentWeatherForecast extends Fragment {

    TextView textViewDay1;
    TextView textViewMinTempValue1;
    TextView textViewMaxTempValue1;
    TextView textViewDescriptionText1;

    TextView textViewDay2;
    TextView textViewMinTempValue2;
    TextView textViewMaxTempValue2;
    TextView textViewDescriptionText2;

    TextView textViewDay3;
    TextView textViewMinTempValue3;
    TextView textViewMaxTempValue3;
    TextView textViewDescriptionText3;

    TextView textViewDay4;
    TextView textViewMinTempValue4;
    TextView textViewMaxTempValue4;
    TextView textViewDescriptionText4;

    TextView textViewDay5;
    TextView textViewMinTempValue5;
    TextView textViewMaxTempValue5;
    TextView textViewDescriptionText5;

    TextView textViewDay6;
    TextView textViewMinTempValue6;
    TextView textViewMaxTempValue6;
    TextView textViewDescriptionText6;

    TextView textViewDay7;
    TextView textViewMinTempValue7;
    TextView textViewMaxTempValue7;
    TextView textViewDescriptionText7;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_weather_forecast, container, false);

        textViewDay1 = view.findViewById(R.id.TextViewDayValue1);
        textViewMinTempValue1 = view.findViewById(R.id.TextViewMinTempValue1);
        textViewMaxTempValue1 = view.findViewById(R.id.TextViewMaxTempValue1);
        textViewDescriptionText1 = view.findViewById(R.id.TextViewDescriptionText1);

        textViewDay2 = view.findViewById(R.id.TextViewDayValue2);
        textViewMinTempValue2 = view.findViewById(R.id.TextViewMinTempValue2);
        textViewMaxTempValue2 = view.findViewById(R.id.TextViewMaxTempValue2);
        textViewDescriptionText2 = view.findViewById(R.id.TextViewDescriptionText2);

        textViewDay3 = view.findViewById(R.id.TextViewDayValue3);
        textViewMinTempValue3 = view.findViewById(R.id.TextViewMinTempValue3);
        textViewMaxTempValue3 = view.findViewById(R.id.TextViewMaxTempValue3);
        textViewDescriptionText3 = view.findViewById(R.id.TextViewDescriptionText3);

        textViewDay4 = view.findViewById(R.id.TextViewDayValue4);
        textViewMinTempValue4 = view.findViewById(R.id.TextViewMinTempValue4);
        textViewMaxTempValue4 = view.findViewById(R.id.TextViewMaxTempValue4);
        textViewDescriptionText4 = view.findViewById(R.id.TextViewDescriptionText4);

        textViewDay5 = view.findViewById(R.id.TextViewDayValue5);
        textViewMinTempValue5 = view.findViewById(R.id.TextViewMinTempValue5);
        textViewMaxTempValue5 = view.findViewById(R.id.TextViewMaxTempValue5);
        textViewDescriptionText5 = view.findViewById(R.id.TextViewDescriptionText5);

        textViewDay6 = view.findViewById(R.id.TextViewDayValue6);
        textViewMinTempValue6 = view.findViewById(R.id.TextViewMinTempValue6);
        textViewMaxTempValue6 = view.findViewById(R.id.TextViewMaxTempValue6);
        textViewDescriptionText6 = view.findViewById(R.id.TextViewDescriptionText6);

        textViewDay7 = view.findViewById(R.id.TextViewDayValue7);
        textViewMinTempValue7 = view.findViewById(R.id.TextViewMinTempValue7);
        textViewMaxTempValue7 = view.findViewById(R.id.TextViewMaxTempValue7);
        textViewDescriptionText7 = view.findViewById(R.id.TextViewDescriptionText7);

        update();

        return view;
    }

    @SuppressLint("SetTextI18n")
    public void update() {
        try{
            ForecastModel forecastModel = ((MainForWeatherFragment) Objects.requireNonNull(getActivity())).forecastModel;

            textViewDay1.setText(forecastModel.forecasts[0].day);
            textViewDay2.setText(forecastModel.forecasts[1].day);
            textViewDay3.setText(forecastModel.forecasts[2].day);
            textViewDay4.setText(forecastModel.forecasts[3].day);
            textViewDay5.setText(forecastModel.forecasts[4].day);
            textViewDay6.setText(forecastModel.forecasts[5].day);
            textViewDay7.setText(forecastModel.forecasts[6].day);

            if(MainWeatherActivity.getUnit().equals("metric")){
                textViewMinTempValue1.setText(forecastModel.forecasts[0].low + " C");
                textViewMaxTempValue1.setText(forecastModel.forecasts[0].high + " C");
                textViewMinTempValue2.setText(forecastModel.forecasts[1].low + " C");
                textViewMaxTempValue2.setText(forecastModel.forecasts[1].high + " C");
                textViewMinTempValue3.setText(forecastModel.forecasts[2].low + " C");
                textViewMaxTempValue3.setText(forecastModel.forecasts[2].high + " C");
                textViewMinTempValue4.setText(forecastModel.forecasts[3].low + " C");
                textViewMaxTempValue4.setText(forecastModel.forecasts[3].high + " C");
                textViewMinTempValue5.setText(forecastModel.forecasts[4].low + " C");
                textViewMaxTempValue5.setText(forecastModel.forecasts[4].high + " C");
                textViewMinTempValue6.setText(forecastModel.forecasts[5].low + " C");
                textViewMaxTempValue6.setText(forecastModel.forecasts[5].high + " C");
                textViewMinTempValue7.setText(forecastModel.forecasts[6].low + " C");
                textViewMaxTempValue7.setText(forecastModel.forecasts[6].high + " C");
            }else {
                textViewMinTempValue1.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[0].low)))+" F");
                textViewMaxTempValue1.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[0].high)))+" F");
                textViewMinTempValue2.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[1].low)))+" F");
                textViewMaxTempValue2.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[1].high)))+" F");
                textViewMinTempValue3.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[2].low)))+" F");
                textViewMaxTempValue3.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[2].high)))+" F");
                textViewMinTempValue4.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[3].low)))+" F");
                textViewMaxTempValue4.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[3].high)))+" F");
                textViewMinTempValue5.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[4].low)))+" F");
                textViewMaxTempValue5.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[4].high)))+" F");
                textViewMinTempValue6.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[5].low)))+" F");
                textViewMaxTempValue6.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[5].high)))+" F");
                textViewMinTempValue7.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[6].low)))+" F");
                textViewMaxTempValue7.setText((int)(32 + (1.8 * Double.parseDouble(forecastModel.forecasts[6].high)))+" F");
            }

            textViewDescriptionText1.setText(forecastModel.forecasts[0].text);
            textViewDescriptionText2.setText(forecastModel.forecasts[1].text);
            textViewDescriptionText3.setText(forecastModel.forecasts[2].text);
            textViewDescriptionText4.setText(forecastModel.forecasts[3].text);
            textViewDescriptionText5.setText(forecastModel.forecasts[4].text);
            textViewDescriptionText6.setText(forecastModel.forecasts[5].text);
            textViewDescriptionText7.setText(forecastModel.forecasts[6].text);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
