package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.chen.xyweather.bean.DailyForecast;

import java.util.ArrayList;

/**
 * Created by chen on 17-4-10.
 * 封装预报天气的数据
 */
public class ForecastData implements Parcelable{

    public Basic basic;
    public ArrayList<DailyForecast> dailyForecasts;
    public String status;

    public ForecastData() {
    }

    protected ForecastData(Parcel in) {
        basic = in.readParcelable(Basic.class.getClassLoader());
        dailyForecasts = in.createTypedArrayList(DailyForecast.CREATOR);
        status = in.readString();
    }

    public static final Creator<ForecastData> CREATOR = new Creator<ForecastData>() {
        @Override
        public ForecastData createFromParcel(Parcel in) {
            return new ForecastData(in);
        }

        @Override
        public ForecastData[] newArray(int size) {
            return new ForecastData[size];
        }
    };

    public Basic getBasic() {
        return basic;
    }

    public ArrayList<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public String getStatus() {
        return status;
    }

    @JSONField(name = "basic")
    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    @JSONField(name = "daily_forecast")
    public void setDailyForecasts(ArrayList<DailyForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

    @JSONField(name = "status")
    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(basic, flags);
        dest.writeTypedList(dailyForecasts);
        dest.writeString(status);
    }
}
