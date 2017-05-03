package com.chen.xyweather.model;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;

/**
 * Created by chen on 17-5-3.
 * user
 */
public class UserModel extends AVUser {

    public UserModel() {

    }

    //头像
    public void setAvatar(AVFile avatar) {
        put("avatar", avatar);
    }

    public AVFile getAvatar() {
        return this.getAVFile("avatar");
    }

    public void setNickName(String name) {
        put("nickname", name);
    }

    public String getNickName() {
        return this.getString("nickname");
    }

    public void setCity(String city) {
        put("city", city);
    }

    public String getCity() {
        return this.getString("city");
    }
}
