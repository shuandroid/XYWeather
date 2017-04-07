package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 17-3-23.
 * 实况天气
 */
public class Now implements Parcelable{

    public Cond_ cond_;
    public String fl;
    public String hum;
    public String pcpn;
    public String pres;
    public String tmp;
    public String vis;

    public Wind wind;

    public Now() {

    }

    protected Now(Parcel in) {
        cond_ = in.readParcelable(Cond_.class.getClassLoader());
        fl = in.readString();
        hum = in.readString();
        pcpn = in.readString();
        pres = in.readString();
        tmp = in.readString();
        vis = in.readString();
        wind = in.readParcelable(Wind.class.getClassLoader());
    }

    public static final Creator<Now> CREATOR = new Creator<Now>() {
        @Override
        public Now createFromParcel(Parcel in) {
            return new Now(in);
        }

        @Override
        public Now[] newArray(int size) {
            return new Now[size];
        }
    };

    @JSONField(name = "cond")
    public void setCond_(Cond_ cond_) {
        this.cond_ = cond_;
    }

    @JSONField(name = "fl")
    public void setFl(String fl) {
        this.fl = fl;
    }

    @JSONField(name = "hum")
    public void setHum(String hum) {
        this.hum = hum;
    }

    @JSONField(name = "pcpn")
    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    @JSONField(name = "pres")
    public void setPres(String pres) {
        this.pres = pres;
    }

    @JSONField(name = "tmp")
    public void setTmp(String tmp) {
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


    public Cond_ getCond_() {
        return cond_;
    }

    public String getFl() {
        return fl;
    }

    public String getHum() {
        return hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public String getPres() {
        return pres;
    }

    public String getTmp() {
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
        dest.writeParcelable(cond_, flags);
        dest.writeString(fl);
        dest.writeString(hum);
        dest.writeString(pcpn);
        dest.writeString(pres);
        dest.writeString(tmp);
        dest.writeString(vis);
        dest.writeParcelable(wind, flags);
    }
}
