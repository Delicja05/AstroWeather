package com.example.astroweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class ViewPagerAdapterAstro extends FragmentPagerAdapter {

    Double latitude;
    Double longitude;
    int timeRefresh;
    private int COUNT = 2;

    ViewPagerAdapterAstro(FragmentManager fm, Double latitude, Double longitude, int timeRefresh) {

        super(fm);
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeRefresh = timeRefresh;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                Log.d("fragment initialize",latitude + " " + longitude + " " + timeRefresh);
                fragment = new FragmentActivitySun();
                break;
            case 1:
                fragment = new FragmentActivityMoon();
                break;
        }
        Log.d("ViewPagerAdapterAstro:", String.valueOf(latitude) + " " + String.valueOf(longitude) + " " + timeRefresh);
        Bundle data = new Bundle();
        data.putDouble("latitude", latitude);
        data.putDouble("longitude", longitude);
        data.putInt("timerefresh", timeRefresh);
        fragment.setArguments(data);

        return fragment;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return "Sun";
        else
            return "Moon";
    }
}