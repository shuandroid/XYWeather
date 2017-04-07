package com.chen.xyweather.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSONObject;
import com.chen.xyweather.R;
import com.chen.xyweather.api.ApiManger;
import com.chen.xyweather.api.WeatherManger;
import com.chen.xyweather.base.BaseFragment;
import com.chen.xyweather.bean.Weather;
import com.chen.xyweather.bean.entity.WeatherData;
import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.view.DailyForecastView;
import com.chen.xyweather.view.HourlyForecastView;
import com.chen.xyweather.view.drawer.BaseDrawer;
import com.chen.xyweather.view.widget.AqiView;
import com.chen.xyweather.view.widget.AstroView;
import com.chen.xyweather.view.widget.PullRefreshLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 天气 fragment 主界面
 */
public class WeatherFragment extends BaseFragment {

    private WeatherManger weather;

    @Bind(R.id.pull_refresh)
    protected PullRefreshLayout pullRefreshLayout;

    @Bind(R.id.scrollView)
    protected ScrollView scrollView;


    @Bind(R.id.w_dailyForecast)
    protected DailyForecastView dailyForecastView;

    @Bind(R.id.hourly_forecast)
    protected HourlyForecastView hourlyForecastView;

    @Bind(R.id.w_astro_view)
    protected AstroView astroView;

    @Bind(R.id.w_aqi_view)
    protected AqiView aqiView;



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
        @SuppressLint("InflateParams")
        View rootView = inflater.inflate(R.layout.fragment_weather, null);

        ButterKnife.bind(this, rootView);


        init();

        pullListener();

        updateWeatherUI();
        return rootView;
    }

    /**
     * 为基本数据赋值
     */
    private void updateWeatherUI() {


    }

    /**
     * 刷新
     */
    private void pullListener() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


            }
        });
    }



    /**
     * 初始化
     */
    private void init() {


        loadCity();
    }

    /**
     *  初始加载城市
     */
    private void loadCity() {

        String city = "武汉";
        DebugLog.e("load city");
        WeatherManger.searchWeatherByCity(city, new WeatherManger.WeatherApiCallback() {

            @Override
            public void onFailure(Throwable t) {

                DebugLog.e("throwable + search" + t);
            }

            @Override
            public void onResponse(int code, String message) {
                DebugLog.e("response" + message );
                DebugLog.e("code" + code);

                try {
                    Weather weather = JSONObject.parseObject(message, Weather.class);
                    if (!ApiManger.acceptWeather(weather)) {
                        return;
                    }

                    WeatherData weatherData = weather.get();
                    DebugLog.e("weather" + weather);
                    DebugLog.e("weather data" + weatherData);

                    updateWeatherUI();

                } catch (Exception e) {
                    e.printStackTrace();
                    DebugLog.e("load city error");
                }

            }
        });


    }





}
