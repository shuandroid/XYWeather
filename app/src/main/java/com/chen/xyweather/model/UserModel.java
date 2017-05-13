package com.chen.xyweather.model;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.concurrent.TimeUnit;

/**
 * Created by chen on 17-5-3.
 * user
 */
public class UserModel extends AVUser {

    public static final String AVATAR = "avatar";

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

    public static UserModel getCurrentUser() {
        return getCurrentUser(UserModel.class);
    }


    public static String getCurrentUserId() {
        UserModel currentUser = getCurrentUser();
        return (null != currentUser ? currentUser.getObjectId() : null);
    }

    public String getAvatarUrl() {
        AVFile avatar = getAVFile(AVATAR);
        if (avatar != null) {
            return avatar.getUrl();
        } else {
            return null;
        }
    }


    public void findFriendsWithCachePolicy(AVQuery.CachePolicy cachePolicy, FindCallback<UserModel>
            findCallback) {
        AVQuery<UserModel> q = null;
        try {
            q = followeeQuery(UserModel.class);
        } catch (Exception e) {
        }
        q.setCachePolicy(cachePolicy);
        q.setMaxCacheAge(TimeUnit.MINUTES.toMillis(1));
        q.findInBackground(findCallback);
    }

}
