package com.chen.xyweather.utils;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.chen.xyweather.model.UserModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoutao on 17/5/11.
 * 真心不知道在这里写个application 是干嘛的……看不懂
 */
public class App extends Application {
    public static final String LIKES = "likes";
    public static final String STATUS_DETAIL = "StatusDetail";
    public static final String DETAIL_ID = "detailId";
    public static final String CREATED_AT = "createdAt";
    public static final String FOLLOWER = "follower";
    public static final String FOLLOWEE = "followee";

    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "9KWSy8VuDTOGJzGxvQW46UIR-gzGzoHsz",
                "pKFyE7QhEN9PCOGIafXwMqmv");
        AVOSCloud.setDebugLogEnabled(true);
        initImageLoader(this);
    }

    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "");
        ImageLoaderConfiguration config = StatusUtils.getImageLoaderConfig(context, cacheDir);
        ImageLoader.getInstance().init(config);
    }

    public static Map<String, UserModel> userCache = new HashMap<>();

    public static void registerUser(UserModel user) {
        userCache.put(user.getObjectId(), user);
    }

    public static void registerBatchUser(List<UserModel> users) {
        for (UserModel user : users) {
            registerUser(user);
        }
    }

    public static UserModel lookupUser(String userId) {
        return userCache.get(userId);
    }
}
