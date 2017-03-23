package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 17-3-23.
 * 天气状况 夜间和白天
 */
public class Cond_  implements Parcelable{

    public String code;
    public String txt;

    protected Cond_(Parcel in) {
        code = in.readString();
        txt = in.readString();
    }

    public static final Creator<Cond_> CREATOR = new Creator<Cond_>() {
        @Override
        public Cond_ createFromParcel(Parcel in) {
            return new Cond_(in);
        }

        @Override
        public Cond_[] newArray(int size) {
            return new Cond_[size];
        }
    };

    public String getCode() {
        return code;
    }

    public String getTxt() {
        return txt;
    }

    @JSONField(name = "code")
    public void setCode(String code) {
        this.code = code;
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
        dest.writeString(code);
        dest.writeString(txt);
    }
}
