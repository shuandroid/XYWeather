package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 17-3-23.
 * comf 舒适度指数
 */
public class Comf implements Parcelable{

    public String brf;
    public String txt;


    public Comf() {
    }
    protected Comf(Parcel in) {
        brf = in.readString();
        txt = in.readString();
    }

    public static final Creator<Comf> CREATOR = new Creator<Comf>() {
        @Override
        public Comf createFromParcel(Parcel in) {
            return new Comf(in);
        }

        @Override
        public Comf[] newArray(int size) {
            return new Comf[size];
        }
    };

    public String getBrf() {
        return brf;
    }

    public String getTxt() {
        return txt;
    }

    @JSONField(name = "brf")
    public void setBrf(String brf) {
        this.brf = brf;
    }

    @JSONField(name = "txt")
    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(brf);
        dest.writeString(txt);
    }
}
