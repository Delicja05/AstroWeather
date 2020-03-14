package com.example.astroweather;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MainForWeatherFragment extends AppCompatActivity  {

    String city;
    String unit;

    Button refresh;
    ForecastModel forecastModel;
    String dataFormat;
    boolean showErrorToast = true;

    FragmentBasicData fragmentBasicData = new FragmentBasicData();
    FragmentAdditionalData fragmentAdditionalData = new FragmentAdditionalData();
    FragmentWeatherForecast fragmentWeatherForecast = new FragmentWeatherForecast();

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ViewPager pager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_pager_weather);

        city = getIntent().getStringExtra("city");
        unit = getIntent().getStringExtra("unit");

        refresh = findViewById(R.id.button_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData(true);
            }
        });

        dataFormat="&u=c";
        refreshData(false);

        Log.d("MainForWeatherFragment:",city + unit);

        setViewAdapter(savedInstanceState);

        refreshData(true);

    }

    void setViewAdapter(Bundle savedInstanceState){

        int screensize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        int orientation = getResources().getConfiguration().orientation;

        if ((orientation != Configuration.ORIENTATION_PORTRAIT && screensize != Configuration.SCREENLAYOUT_SIZE_NORMAL)
                || (orientation != Configuration.ORIENTATION_LANDSCAPE && screensize != Configuration.SCREENLAYOUT_SIZE_NORMAL) && savedInstanceState==null) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.basicFragment,fragmentBasicData,"fragmentBasicInfo");
            fragmentTransaction.add(R.id.additionalFragment,fragmentAdditionalData,"fragmentAdvancedInfo");
            fragmentTransaction.add(R.id.forecastFragment,fragmentWeatherForecast,"fragmentFutureInfo");
            fragmentTransaction.commit();

        }
        else if ((orientation != Configuration.ORIENTATION_PORTRAIT && screensize != Configuration.SCREENLAYOUT_SIZE_NORMAL)
                || (orientation != Configuration.ORIENTATION_LANDSCAPE && screensize != Configuration.SCREENLAYOUT_SIZE_NORMAL) && savedInstanceState!=null)
        {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.basicFragment,fragmentBasicData,"fragmentBasicInfo");
            fragmentTransaction.replace(R.id.additionalFragment,fragmentAdditionalData,"fragmentAdvancedInfo");
            fragmentTransaction.replace(R.id.forecastFragment,fragmentWeatherForecast,"fragmentFutureInfo");
            fragmentTransaction.commit();
        }
        else {
            pager = findViewById(R.id.viewPager);
            pagerAdapter = new ViewPagerAdapterWeather(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("data",dataFormat);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dataFormat=savedInstanceState.getString("data");
        refreshData(false);
        showErrorToast=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void updateFragments()
    {
        fragmentBasicData.update();
        fragmentAdditionalData.update();
        fragmentWeatherForecast.update();
    }

    public void refreshData(final boolean fromButton)
    {
        SendRequest request = new SendRequest(Request.Method.GET, null, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                SharedPreferences sharedPreferences = getSharedPreferences(city, MODE_PRIVATE);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                String jsonString = response.toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(city, jsonString);
                editor.commit();
                forecastModel = gson.fromJson(jsonString, ForecastModel.class);
                updateFragments();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(fromButton)
                    Toast.makeText(getApplicationContext(), "It was not possible to connect from the base. Downloading from memory.", Toast.LENGTH_LONG).show();
                SharedPreferences sharedPreferences = getSharedPreferences(city, MODE_PRIVATE);
                String jsonString = sharedPreferences.getString(city,"");
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                forecastModel = gson.fromJson(jsonString, ForecastModel.class);
                updateFragments();
            }
        });
        RequestManager requestManager = RequestManager.getInstance(this);
        request.setCity(city);
        request.setDataFormat(dataFormat);
        requestManager.addToRequestQueue(request);
    }

    class ViewPagerAdapterWeather extends PagerAdapter {

        FragmentManager fragmentManager;
        Fragment[] fragments;

        ViewPagerAdapterWeather(FragmentManager fm) {
            fragmentManager = fm;
            fragments = new Fragment[3];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            assert(0 <= position && position < fragments.length);
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.remove(fragments[position]);
            trans.commit();
            fragments[position] = null;
        }

        @Override
        public Fragment instantiateItem(ViewGroup container, int position){
            Fragment fragment = getItem(position);
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.add(container.getId(),fragment,"fragment:"+position);
            trans.commit();
            return fragment;
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object fragment) {
            return ((Fragment) fragment).getView() == view;
        }

        public Fragment getItem(int position){
            assert(0 <= position && position < fragments.length);
            if(fragments[position] == null){
                if(position==0)
                    fragments[position] = (fragmentBasicData = new FragmentBasicData());
                else if(position==1)
                    fragments[position] = (fragmentAdditionalData = new FragmentAdditionalData());
                else if (position==2)
                    fragments[position] = (fragmentWeatherForecast = new FragmentWeatherForecast());
            }
            return fragments[position];
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0)
                return "Basic Data";
            else if(position == 1)
                return "Additional Data";
            else if(position == 2)
                return "Weather Forecast";
            else
                return "";
        }
    }

}