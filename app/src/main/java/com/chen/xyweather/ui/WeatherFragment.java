package com.chen.xyweather.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseFragment;
import com.chen.xyweather.view.drawer.BaseDrawer;

/**
 *  天气 fragment
 */
    public class WeatherFragment extends BaseFragment {


    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public BaseDrawer.Type getDrawerType() {
        return null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }



}
