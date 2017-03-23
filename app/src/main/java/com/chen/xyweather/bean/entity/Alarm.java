package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 17-3-23.
 * 天气预警
 */
public class Alarm implements Parcelable {
    public String level;
    public String stat;
    public String title;
    public String txt;
    public String type;

    public Alarm() {
    }

    protected Alarm(Parcel in) {
        level = in.readString();
        stat = in.readString();
        title = in.readString();
        txt = in.readString();
        type = in.readString();
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    @JSONField(name = "level")
    public void setLevel(String level) {
        this.level = level;
    }

    @JSONField(name = "stat")
    public void setStat(String stat) {
        this.stat = stat;
    }

    @JSONField(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JSONField(name = "txt")
    public void setTxt(String txt) {
        this.txt = txt;
    }

    @JSONField(name = "type")
    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public String getStat() {
        return stat;
    }

    public String getTitle() {
        return title;
    }

    public String getTxt() {
        return txt;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(level);
        dest.writeString(stat);
        dest.writeString(title);
        dest.writeString(txt);
        dest.writeString(type);
    }
}
