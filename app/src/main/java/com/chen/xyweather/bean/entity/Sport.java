package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 17-3-23.
 * 运动
 */
public class Sport implements Parcelable {

    public String brf;
    public String txt;

    protected Sport(Parcel in) {
        brf = in.readString();
        txt = in.readString();
    }

    public static final Creator<Sport> CREATOR = new Creator<Sport>() {
        @Override
        public Sport createFromParcel(Parcel in) {
            return new Sport(in);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
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
