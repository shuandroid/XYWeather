package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.chen.xyweather.bean.City;

/**
 * Created by chen on 17-3-23.
 * api
 */
public class Api  implements Parcelable {

    public City city;

    public Api() {

    }

    protected Api(Parcel in) {
        city = in.readParcelable(City.class.getClassLoader());
    }

    public static final Creator<Api> CREATOR = new Creator<Api>() {
        @Override
        public Api createFromParcel(Parcel in) {
            return new Api(in);
        }

        @Override
        public Api[] newArray(int size) {
            return new Api[size];
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
