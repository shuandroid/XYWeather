package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.chen.xyweather.bean.City;

/**
 * Created by chen on 17-3-23.
 * aqi
 */
public class Aqi implements Parcelable {

    public City city;

    public Aqi() {

    }

    protected Aqi(Parcel in) {
        city = in.readParcelable(City.class.getClassLoader());
    }

    public static final Creator<Aqi> CREATOR = new Creator<Aqi>() {
        @Override
        public Aqi createFromParcel(Parcel in) {
            return new Aqi(in);
        }

        @Override
        public Aqi[] newArray(int size) {
            return new Aqi[size];
        }
    };

    public City getCity() {
        return city;
    }

    @JSONField(name = "city")
    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(city, flags);
    }
}
