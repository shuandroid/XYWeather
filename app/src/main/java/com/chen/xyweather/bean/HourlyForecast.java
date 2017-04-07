package com.chen.xyweather.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.chen.xyweather.bean.entity.Cond_;
import com.chen.xyweather.bean.entity.Wind;

/**
 * Created by chen on 17-3-23.
 * 时刻预报
 */
public class HourlyForecast implements Parcelable {

    public Cond_ cond;
    public String date;
    public String hum;
    public String pop;
    public String pres;
    public String tmp;
    public Wind wind;

    public HourlyForecast() {

    }

    protected HourlyForecast(Parcel in) {
        cond = in.readParcelable(Cond_.class.getClassLoader());
        date = in.readString();
        hum = in.readString();
        pop = in.readString();
        pres = in.readString();
        tmp = in.readString();
        wind = in.readParcelable(Wind.class.getClassLoader());
    }

    public static final Creator<HourlyForecast> CREATOR = new Creator<HourlyForecast>() {
        @Override
        public HourlyForecast createFromParcel(Parcel in) {
            return new HourlyForecast(in);
        }

        @Override
        public HourlyForecast[] newArray(int size) {
            return new HourlyForecast[size];
        }
    };

    @JSONField(name = "cond")
    public void setCond(Cond_ cond) {
        this.cond = cond;
    }

    @JSONField(name = "date")
    public void setDate(String date) {
        this.date = date;
    }

    @JSONField(name = "hum")
    public void setHum(String hum) {
        this.hum = hum;
    }

    @JSONField(name = "pop")
    public void setPop(String pop) {
        this.pop = pop;
    }

    @JSONField(name = "pres")
    public void setPres(String pres) {
        this.pres = pres;
    }

    @JSONField(name = "tmp")
    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    @JSONField(name = "wind")
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Cond_ getCond() {
        return cond;
    }

    public String getDate() {
        return date;
    }

    public String getHum() {
        return hum;
    }

    public String getPop() {
        return pop;
    }

    public String getPres() {
        return pres;
    }

    public String getTmp() {
        return tmp;
    }

    public Wind getWind() {
        return wind;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(cond, flags);
        dest.writeString(date);
        dest.writeString(hum);
        dest.writeString(pop);
        dest.writeString(pres);
        dest.writeString(tmp);
        dest.writeParcelable(wind, flags);
    }
}
