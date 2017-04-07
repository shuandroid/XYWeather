package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.chen.xyweather.bean.DailyForecast;
import com.chen.xyweather.bean.HourlyForecast;

import java.util.ArrayList;

/**
 * Created by chen on 17-3-23.
 * 和风api
 */
public class WeatherData implements Parcelable{

    public ArrayList<Alarm> alarms;
    public Aqi aqi;
    public Basic basic;
    public ArrayList<DailyForecast> dailyForecasts;
    public ArrayList<HourlyForecast> hourlyForecasts;
    public Now now;
    public String status;
    public Suggestion suggestion;

    public WeatherData() {

    }

    protected WeatherData(Parcel in) {
        alarms = in.createTypedArrayList(Alarm.CREATOR);
        aqi = in.readParcelable(Aqi.class.getClassLoader());
        basic = in.readParcelable(Basic.class.getClassLoader());
        dailyForecasts = in.createTypedArrayList(DailyForecast.CREATOR);
        hourlyForecasts = in.createTypedArrayList(HourlyForecast.CREATOR);
        now = in.readParcelable(Now.class.getClassLoader());
        status = in.readString();
        suggestion = in.readParcelable(Suggestion.class.getClassLoader());
    }

    public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel in) {
            return new WeatherData(in);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };

    public ArrayList<Alarm> getAlarms() {
        return alarms;
    }

    public Aqi getAqi() {
        return aqi;
    }

    public Basic getBasic() {
        return basic;
    }

    public ArrayList<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public ArrayList<HourlyForecast> getHourlyForecasts() {
        return hourlyForecasts;
    }

    public Now getNow() {
        return now;
    }

    public String getStatus() {
        return status;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    @JSONField(name = "alarms")
    public void setAlarms(ArrayList<Alarm> alarms) {
        this.alarms = alarms;
    }

    @JSONField(name = "aqi")
    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }

    @JSONField(name = "basic")
    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    @JSONField(name = "daily_forecast")
    public void setDailyForecasts(ArrayList<DailyForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

    @JSONField(name = "hourly_forecast")
    public void setHourlyForecasts(ArrayList<HourlyForecast> hourlyForecasts) {
        this.hourlyForecasts = hourlyForecasts;
    }

    @JSONField(name = "now")
    public void setNow(Now now) {
        this.now = now;
    }

    @JSONField(name = "status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JSONField(name = "suggestion")
    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(alarms);
        dest.writeParcelable(aqi, flags);
        dest.writeParcelable(basic, flags);
        dest.writeTypedList(dailyForecasts);
        dest.writeTypedList(hourlyForecasts);
        dest.writeParcelable(now, flags);
        dest.writeString(status);
        dest.writeParcelable(suggestion, flags);
    }
}
