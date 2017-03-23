package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 17-3-23.
 * basic 基本信息
 */
public class Basic implements Parcelable {

    public String city;
    public String cnty;
    public String id;
    public String lat;
    public String lon;

    public Update update;

    public Basic() {

    }

    protected Basic(Parcel in) {
        city = in.readString();
        cnty = in.readString();
        id = in.readString();
        lat = in.readString();
        lon = in.readString();
        update = in.readParcelable(Update.class.getClassLoader());
    }

    public static final Creator<Basic> CREATOR = new Creator<Basic>() {
        @Override
        public Basic createFromParcel(Parcel in) {
            return new Basic(in);
        }

        @Override
        public Basic[] newArray(int size) {
            return new Basic[size];
        }
    };

    public String getCity() {
        return city;
    }

    public String getCnty() {
        return cnty;
    }

    public String getId() {
        return id;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public Update getUpdate() {
        return update;
    }

    @JSONField(name = "city")
    public void setCity(String city) {
        this.city = city;
    }

    @JSONField(name = "cnty")
    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    @JSONField(name = "id")
    public void setId(String id) {
        this.id = id;
    }

    @JSONField(name = "lat")
    public void setLat(String lat) {
        this.lat = lat;
    }

    @JSONField(name = "lon")
    public void setLon(String lon) {
        this.lon = lon;
    }

    @JSONField(name = "update")
    public void setUpdate(Update update) {
        this.update = update;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(cnty);
        dest.writeString(id);
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeParcelable(update, flags);
    }
}
