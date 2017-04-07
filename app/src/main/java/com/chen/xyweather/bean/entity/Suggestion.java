package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 17-3-23.
 * 建议
 */
public class Suggestion implements Parcelable {

    public Comf comf;
    public Cw cw;
    public Drsg drsg;
    public Flu flu;
    public Sport sport;
    public Trav trav;
    public Uv uv;

    public Suggestion() {

    }

    protected Suggestion(Parcel in) {
        comf = in.readParcelable(Comf.class.getClassLoader());
        cw = in.readParcelable(Cw.class.getClassLoader());
        drsg = in.readParcelable(Drsg.class.getClassLoader());
        flu = in.readParcelable(Flu.class.getClassLoader());
        sport = in.readParcelable(Sport.class.getClassLoader());
        trav = in.readParcelable(Trav.class.getClassLoader());
        uv = in.readParcelable(Uv.class.getClassLoader());
    }

    public static final Creator<Suggestion> CREATOR = new Creator<Suggestion>() {
        @Override
        public Suggestion createFromParcel(Parcel in) {
            return new Suggestion(in);
        }

        @Override
        public Suggestion[] newArray(int size) {
            return new Suggestion[size];
        }
    };

    @JSONField(name = "comf")
    public void setComf(Comf comf) {
        this.comf = comf;
    }

    @JSONField(name = "cw")
    public void setCw(Cw cw) {
        this.cw = cw;
    }

    @JSONField(name = "drsg")
    public void setDrsg(Drsg drsg) {
        this.drsg = drsg;
    }

    @JSONField(name = "flu")
    public void setFlu(Flu flu) {
        this.flu = flu;
    }

    @JSONField(name = "sport")
    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @JSONField(name = "trav")
    public void setTrav(Trav trav) {
        this.trav = trav;
    }

    @JSONField(name = "uv")
    public void setUv(Uv uv) {
        this.uv = uv;
    }

    public Comf getComf() {
        return comf;
    }

    public Cw getCw() {
        return cw;
    }

    public Drsg getDrsg() {
        return drsg;
    }

    public Flu getFlu() {
        return flu;
    }

    public Sport getSport() {
        return sport;
    }

    public Trav getTrav() {
        return trav;
    }

    public Uv getUv() {
        return uv;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(comf, flags);
        dest.writeParcelable(cw, flags);
        dest.writeParcelable(drsg, flags);
        dest.writeParcelable(flu, flags);
        dest.writeParcelable(sport, flags);
        dest.writeParcelable(trav, flags);
        dest.writeParcelable(uv, flags);
    }
}
