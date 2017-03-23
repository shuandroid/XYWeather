package com.chen.xyweather.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 17-3-6.
 * 城市
 */
public class City implements Parcelable {

    public String api;
    public String co;
    public String no2;
    public String o3;
    public String pm10;
    public String pm25;
    public String qlty;
    public String so2;


    public City() {
    }

    protected City(Parcel in) {
        api = in.readString();
        co = in.readString();
        no2 = in.readString();
        o3 = in.readString();
        pm10 = in.readString();
        pm25 = in.readString();
        qlty = in.readString();
        so2 = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public String getApi() {
        return api;
    }

    public String getCo() {
        return co;
    }

    public String getNo2() {
        return no2;
    }

    public String getO3() {
        return o3;
    }

    public String getPm10() {
        return pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public String getQlty() {
        return qlty;
    }

    public String getSo2() {
        return so2;
    }

    @JSONField(name = "api")
    public void setApi(String api) {
        this.api = api;
    }

    @JSONField(name = "co")
    public void setCo(String co) {
        this.co = co;
    }

    @JSONField(name = "no2")
    public void setNo2(String no2) {
        this.no2 = no2;
    }

    @JSONField(name = "o3")
    public void setO3(String o3) {
        this.o3 = o3;
    }

    @JSONField(name = "pm10")
    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    @JSONField(name = "pm25")
    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    @JSONField(name = "qlty")
    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    @JSONField(name = "so2")
    public void setSo2(String so2) {
        this.so2 = so2;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(api);
        dest.writeString(co);
        dest.writeString(no2);
        dest.writeString(o3);
        dest.writeString(pm10);
        dest.writeString(pm25);
        dest.writeString(qlty);
        dest.writeString(so2);
    }
}
