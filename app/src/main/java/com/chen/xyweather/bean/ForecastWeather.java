package com.chen.xyweather.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.chen.xyweather.bean.entity.ForecastData;
import com.chen.xyweather.utils.DebugLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 17-4-10.
 * 另外封装一层 ,暂时不用这个接口？
 */
public class ForecastWeather implements Parcelable{

    public List<ForecastData> forecastDatas = new ArrayList<>();

    public ForecastWeather(){
    }

    public List<ForecastData> getForecastDatas() {
        return forecastDatas;
    }

    @JSONField(name = "HeWeather5")
    public void setForecastDatas(List<ForecastData> forecastDatas) {
        this.forecastDatas = forecastDatas;
    }

    protected ForecastWeather(Parcel in) {
        forecastDatas = in.createTypedArrayList(ForecastData.CREATOR);
    }

    public static final Creator<ForecastWeather> CREATOR = new Creator<ForecastWeather>() {
        @Override
        public ForecastWeather createFromParcel(Parcel in) {
            return new ForecastWeather(in);
        }

        @Override
        public ForecastWeather[] newArray(int size) {
            return new ForecastWeather[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(forecastDatas);
    }

    public ForecastData get() {
        return forecastDatas.get(0);
    }
}
