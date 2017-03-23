package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 17-3-23.
 *  daily forecast 中的时段 分为四个
 */
public class Astro implements Parcelable {

    public String mr;
    public String ms;
    public String sr;
    public String ss;

    protected Astro(Parcel in) {
        sr = in.readString();
        ss = in.readString();
    }

    public static final Creator<Astro> CREATOR = new Creator<Astro>() {
        @Override
        public Astro createFromParcel(Parcel in) {
            return new Astro(in);
        }

        @Override
        public Astro[] newArray(int size) {
            return new Astro[size];
        }
    };

    public String getSr() {
        return sr;
    }

    public String getSs() {
        return ss;
    }

    public String getMr() {
        return mr;
    }

    public String getMs() {
        return ms;
    }

    @JSONField(name = "mr")
    public void setMr(String mr) {
        this.mr = mr;
    }

    @JSONField(name = "ms")
    public void setMs(String ms) {
        this.ms = ms;
    }

    @JSONField(name = "sr")
    public void setSr(String sr) {
        this.sr = sr;
    }

    @JSONField(name = "ss")
    public void setSs(String ss) {
        this.ss = ss;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mr);
        dest.writeString(ms);
        dest.writeString(sr);
        dest.writeString(ss);
    }
}
