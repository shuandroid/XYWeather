package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 17-3-23.
 * 洗车指数
 */
public class Cw implements Parcelable {


    public String brf;
    public String txt;

    public Cw() {

    }

    protected Cw(Parcel in) {
        brf = in.readString();
        txt = in.readString();
    }

    public static final Creator<Cw> CREATOR = new Creator<Cw>() {
        @Override
        public Cw createFromParcel(Parcel in) {
            return new Cw(in);
        }

        @Override
        public Cw[] newArray(int size) {
            return new Cw[size];
        }
    };

    @JSONField(name = "brf")
    public void setBrf(String brf) {
        this.brf = brf;
    }

    @JSONField(name = "txt")
    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getBrf() {
        return brf;
    }

    public String getTxt() {
        return txt;
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
