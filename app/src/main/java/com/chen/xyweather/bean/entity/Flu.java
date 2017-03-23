package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chen on 17-3-23.
 * 感冒指数
 */
public class Flu implements Parcelable {

    public String brf;
    public String txt;

    protected Flu(Parcel in) {
        brf = in.readString();
        txt = in.readString();
    }

    public static final Creator<Flu> CREATOR = new Creator<Flu>() {
        @Override
        public Flu createFromParcel(Parcel in) {
            return new Flu(in);
        }

        @Override
        public Flu[] newArray(int size) {
            return new Flu[size];
        }
    };

    public String getBrf() {
        return brf;
    }

    public String getTxt() {
        return txt;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

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
