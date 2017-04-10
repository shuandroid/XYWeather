package com.chen.xyweather.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.chen.xyweather.R;
import com.chen.xyweather.api.ApiManger;
import com.chen.xyweather.api.WeatherManger;
import com.chen.xyweather.base.BaseFragment;
import com.chen.xyweather.bean.Weather;
import com.chen.xyweather.bean.entity.WeatherData;
import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.utils.UtilManger;
import com.chen.xyweather.view.DailyForecastView;
import com.chen.xyweather.view.DailyForecastViewTest;
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
    private BaseDrawer.Type drawerType;

    private View rootView;


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
        rootView = inflater.inflate(R.layout.fragment_weather, null);

        ButterKnife.bind(this, rootView);

        init();

        pullListener();
        return rootView;
    }

    /**
     * 为基本数据赋值
     */
    private void updateWeatherUI(Weather weather) {

        if (!ApiManger.acceptWeather(weather)) {
            return;
        }

        updateDrawerTypeAndNotify(weather);

        WeatherData weatherData = weather.get();
        DebugLog.e("weather" + weather);
        DebugLog.e("weather data" + weatherData);

        dailyUpdate(weatherData.basic.city);
//        dailyForecastView.setData(weather);
        hourlyForecastView.setData(weather);
        aqiView.setData(weatherData.aqi);
        astroView.setData(weather);

        //当前温度
        final String tmp = weatherData.now.tmp;
        try {
            final int tmp_int = Integer.valueOf(tmp);

            if (tmp_int < 0) {
                setTextView(R.id.now_tmp, String.valueOf(-tmp_int));
                rootView.findViewById(R.id.w_now_tmp_minus).setVisibility(View.VISIBLE);
            } else {
                setTextView(R.id.now_tmp, tmp);
                rootView.findViewById(R.id.w_now_tmp_minus).setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        setTextView(R.id.w_now_cond_text, weatherData.now.cond_.txt);

        //当前时间
        if (UtilManger.isToday(weatherData.basic.update.loc)) {
            setTextView(R.id.w_basic_update_loc, weatherData.basic.update.loc.substring(11) + " 发布");
        } else {
            setTextView(R.id.w_basic_update_loc, weatherData.basic.update.loc.substring(5) + " 发布");
        }

        //详细信息里大号温度
        setTextView(R.id.w_today_bottom_line, weatherData.now.cond_.txt + " " + weather.getTodayTempDescription());
        setTextView(R.id.w_today_temp, weatherData.now.tmp + "°");

        setTextView(R.id.w_now_fl, weatherData.now.fl + "°");
        setTextView(R.id.w_now_pcpn, weatherData.now.pcpn + "mm");
        setTextView(R.id.w_now_hum, weatherData.now.hum + "%");
        setTextView(R.id.w_now_vis, weatherData.now.vis + "km");


        if (weather.hasAqi()) {
            setTextView(R.id.w_aqi_text, weatherData.aqi.city.qlty);

            // TODO: 17-4-7 注意　w_aqi_detail_text
            setTextView(R.id.w_aqi_detail_text, weatherData.aqi.city.qlty);
            setTextView(R.id.w_aqi_pm25, weatherData.aqi.city.pm25 + "μg/m³");
            setTextView(R.id.w_aqi_pm10, weatherData.aqi.city.pm10 + "μg/m³");
            setTextView(R.id.w_aqi_so2, weatherData.aqi.city.so2 + "μg/m³");
            setTextView(R.id.w_aqi_no2, weatherData.aqi.city.no2 + "μg/m³");
        } else {
            setTextView(R.id.w_aqi_text, "");
        }

        //常见建议
        if (weatherData.suggestion != null) {
            setTextView(R.id.w_suggestion_comf, weatherData.suggestion.comf.txt);
            setTextView(R.id.w_suggestion_cw, weatherData.suggestion.cw.txt);
            setTextView(R.id.w_suggestion_drsg, weatherData.suggestion.drsg.txt);
            setTextView(R.id.w_suggestion_flu, weatherData.suggestion.flu.txt);
            setTextView(R.id.w_suggestion_sport, weatherData.suggestion.sport.txt);
            setTextView(R.id.w_suggestion_tarv, weatherData.suggestion.trav.txt);
            setTextView(R.id.w_suggestion_uv, weatherData.suggestion.uv.txt);

            setTextView(R.id.w_suggestion_comf_brf, weatherData.suggestion.comf.brf);
            setTextView(R.id.w_suggestion_cw_brf, weatherData.suggestion.cw.brf);
            setTextView(R.id.w_suggestion_drsg_brf, weatherData.suggestion.drsg.brf);
            setTextView(R.id.w_suggestion_flu_brf, weatherData.suggestion.flu.brf);
            setTextView(R.id.w_suggestion_sport_brf, weatherData.suggestion.sport.brf);
            setTextView(R.id.w_suggestion_tarv_brf, weatherData.suggestion.trav.brf);
            setTextView(R.id.w_suggestion_uv_brf, weatherData.suggestion.uv.brf);
        }


    }

    /**
     * 获取更多天气预报，需要访问另外一个接口
     * @param city city
     */
    private void dailyUpdate(String city) {

        WeatherManger.searchMoreDailyForcest(city, new WeatherManger.WeatherApiCallback() {
            @Override
            public void onFailure(Throwable t) {
                DebugLog.e("throwable + search" + t);
            }

            @Override
            public void onResponse(int code, String message) {

                DebugLog.e("response" + message);
                DebugLog.e("code" + code);

                try {
                    Weather weather = JSONObject.parseObject(message, Weather.class);
                    DebugLog.e("test");
                    updateWeatherUI(weather);

                } catch (Exception e) {
                    e.printStackTrace();
                    DebugLog.e("load city error");
                }
            }
        });

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
     * 初始加载城市
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
                DebugLog.e("response" + message);
                DebugLog.e("code" + code);

                try {
                    Weather weather = JSONObject.parseObject(message, Weather.class);
                    DebugLog.e("test");
                    updateWeatherUI(weather);

                } catch (Exception e) {
                    e.printStackTrace();
                    DebugLog.e("load city error");
                }

            }
        });


    }


    private void updateDrawerTypeAndNotify(Weather weather) {
        drawerType = ApiManger.convertWeatherType(weather);
        notifyActivityUpdate();
    }


    private void setTextView(int textViewId, String s) {
        TextView textView = (TextView) rootView.findViewById(textViewId);
        if (textView != null) {
            textView.setText(s);
        } else {
            toast("TextView id is error" + Integer.toHexString(textViewId));
        }


    }


}
