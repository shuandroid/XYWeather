package com.chen.xyweather.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.chen.xyweather.bean.entity.Astro;
import com.chen.xyweather.bean.entity.Cond;
import com.chen.xyweather.bean.entity.Tmp;
import com.chen.xyweather.bean.entity.Wind;

/**
 * Created by chen on 17-3-23.
 * bean 每日预报
 */
public class DailyForecast implements Parcelable{



    public Astro astro;
    public Cond cond;

    public String date;
    public String hum;
    public String pcpn;
    public String pop;
    public String pres;
    public Tmp tmp;
    public String vis;
    public Wind wind;

    protected DailyForecast() {

    }

    protected DailyForecast(Parcel in) {
        astro = in.readParcelable(Astro.class.getClassLoader());
        cond = in.readParcelable(Cond.class.getClassLoader());
        date = in.readString();
        hum = in.readString();
        pcpn = in.readString();
        pop = in.readString();
        pres = in.readString();
        tmp = in.readParcelable(Tmp.class.getClassLoader());
        vis = in.readString();
        wind = in.readParcelable(Wind.class.getClassLoader());
    }

    public static final Creator<DailyForecast> CREATOR = new Creator<DailyForecast>() {
        @Override
        public DailyForecast createFromParcel(Parcel in) {
            return new DailyForecast(in);
        }

        @Override
        public DailyForecast[] newArray(int size) {
            return new DailyForecast[size];
        }
    };

    @JSONField(name = "astro")
    public void setAstro(Astro astro) {
        this.astro = astro;
    }

    @JSONField(name = "cond")
    public void setCond(Cond cond) {
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

    @JSONField(name = "pcpn")
    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
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
    public void setTmp(Tmp tmp) {
        this.tmp = tmp;
    }

    @JSONField(name = "vis")
    public void setVis(String vis) {
        this.vis = vis;
    }

    @JSONField(name = "wind")
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Astro getAstro() {
        return astro;
    }

    public Cond getCond() {
        return cond;
    }

    public String getDate() {
        return date;
    }

    public String getHum() {
        return hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public String getPop() {
        return pop;
    }

    public String getPres() {
        return pres;
    }

    public Tmp getTmp() {
        return tmp;
    }

    public String getVis() {
        return vis;
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
        dest.writeParcelable(astro, flags);
        dest.writeParcelable(cond, flags);
        dest.writeString(date);
        dest.writeString(hum);
        dest.writeString(pcpn);
        dest.writeString(pop);
        dest.writeString(pres);
        dest.writeParcelable(tmp, flags);
        dest.writeString(vis);
        dest.writeParcelable(wind, flags);
    }
}
