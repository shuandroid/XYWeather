package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 17-3-23.
 * 紫外线指数
 */
public class Uv implements Parcelable {

    public String brf;
    public String txt;

    public Uv() {
    }

    protected Uv(Parcel in) {
        brf = in.readString();
        txt = in.readString();
    }

    public static final Creator<Uv> CREATOR = new Creator<Uv>() {
        @Override
        public Uv createFromParcel(Parcel in) {
            return new Uv(in);
        }

        @Override
        public Uv[] newArray(int size) {
            return new Uv[size];
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
