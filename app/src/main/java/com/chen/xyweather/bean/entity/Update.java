package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chen on 17-3-23.
 * data
 */
public class Update implements Parcelable {

    public String loc;
    public String utc;


    public Update() {
    }

    protected Update(Parcel in) {
        loc = in.readString();
        utc = in.readString();
    }

    public static final Creator<Update> CREATOR = new Creator<Update>() {
        @Override
        public Update createFromParcel(Parcel in) {
            return new Update(in);
        }

        @Override
        public Update[] newArray(int size) {
            return new Update[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loc);
        dest.writeString(utc);
    }
}
