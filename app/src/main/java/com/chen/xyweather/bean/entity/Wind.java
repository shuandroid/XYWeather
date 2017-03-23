package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 17-3-23.
 * 风
 */
public class Wind implements Parcelable{

    public String deg;
    public String dir;
    public String sc;
    public String spd;

    protected Wind(Parcel in) {
        deg = in.readString();
        dir = in.readString();
        sc = in.readString();
        spd = in.readString();
    }

    public static final Creator<Wind> CREATOR = new Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel in) {
            return new Wind(in);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[size];
        }
    };

    @JSONField(name = "deg")
    public void setDeg(String deg) {
        this.deg = deg;
    }

    @JSONField(name = "dir")
    public void setDir(String dir) {
        this.dir = dir;
    }

    @JSONField(name = "sc")
    public void setSc(String sc) {
        this.sc = sc;
    }

    @JSONField(name = "spd")
    public void setSpd(String spd) {
        this.spd = spd;
    }

    public String getDeg() {
        return deg;
    }

    public String getDir() {
        return dir;
    }

    public String getSc() {
        return sc;
    }

    public String getSpd() {
        return spd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deg);
        dest.writeString(dir);
        dest.writeString(sc);
        dest.writeString(spd);
    }
}
