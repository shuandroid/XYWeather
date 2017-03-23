package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 17-3-23.
 * 温度
 */
public class Tmp implements Parcelable{

    public String max;
    public String min;

    public Tmp() {

    }
    protected Tmp(Parcel in) {
        max = in.readString();
        min = in.readString();
    }

    public static final Creator<Tmp> CREATOR = new Creator<Tmp>() {
        @Override
        public Tmp createFromParcel(Parcel in) {
            return new Tmp(in);
        }

        @Override
        public Tmp[] newArray(int size) {
            return new Tmp[size];
        }
    };

    @JSONField(name = "max")
    public void setMax(String max) {
        this.max = max;
    }

    @JSONField(name = "min")
    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public String getMin() {
        return min;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(max);
        dest.writeString(min);
    }
}
