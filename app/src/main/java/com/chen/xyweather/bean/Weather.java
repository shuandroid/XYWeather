package com.chen.xyweather.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.chen.xyweather.api.ApiManger;
import com.chen.xyweather.bean.entity.WeatherData;
import com.chen.xyweather.utils.UtilManger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 17-3-24.
 * zong
 */
public class Weather implements Parcelable {


    public List<WeatherData> weatherDatas = new ArrayList<>(1);

    protected Weather(Parcel in) {
        weatherDatas = in.createTypedArrayList(WeatherData.CREATOR);
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    @JSONField(name = "HeWeather5")
    public void setWeatherDatas(List<WeatherData> weatherDatas) {
        this.weatherDatas = weatherDatas;
    }

    public List<WeatherData> getWeatherDatas() {
        return weatherDatas;
    }

    public Weather() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(weatherDatas);
    }

    /**
     * 判断拉数据是否为真
     *
     * @return 返回真假有数据
     */
    public boolean isOk() {

        if (weatherDatas.size() > 0) {
            final WeatherData weatherData = this.weatherDatas.get(0);
            return TextUtils.equals(weatherData.status, "ok");
        }
        return false;
    }

    public boolean hasAqi() {

        if (isOk()) {
            final WeatherData weatherData = get();
            return weatherData.aqi != null && weatherData.aqi.city != null;
        }

        return false;
    }

    public WeatherData get() {
        return this.weatherDatas.get(0);
    }

    /**
     * @return 返回Index
     */
    public int getTodayDailyForecastIndex() {
        int todayIndex = -1;
        if (!isOk()) {
            return todayIndex;
        }
        final WeatherData weatherData = get();
        for (int i = 0; i < weatherData.dailyForecasts.size(); i++) {
            if (UtilManger.isToday(weatherData.dailyForecasts.get(i).date)) {
                todayIndex = i;
                break;
            }
        }

        return todayIndex;

    }

    /**
     * @return 返回今日dailyForecast
     */
    public DailyForecast getDailyForecast() {
        final int todayIndex = getTodayDailyForecastIndex();
        if (todayIndex != -1) {
            return get().dailyForecasts.get(todayIndex);
        }
        return null;
    }

    /**
     * 气温
     *
     * @return 返回天气
     */
    public String getTodayTempDescription() {

        if (getDailyForecast() != null) {
            return getDailyForecast().tmp.min + "~" + getDailyForecast().tmp.max + "°";
        }

        return "无数据";
    }

    public static String updateTime(WeatherData w) {
        try {

            if (UtilManger.isToday(w.basic.update.loc)) {
                return w.basic.update.loc.substring(11) + " 发布";
            } else
                return w.basic.update.loc.substring(5) + " 发布";
        } catch (Exception e) {

            e.printStackTrace();
        }
        return "? 发布";
    }

}
