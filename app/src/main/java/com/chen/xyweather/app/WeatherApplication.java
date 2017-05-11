package com.chen.xyweather.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.chen.xyweather.model.CustomUserProvider;

import cn.leancloud.chatkit.LCChatKit;

/**
 * Created by chen on 17-3-14.
 * application
 */
public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String appId = "9KWSy8VuDTOGJzGxvQW46UIR-gzGzoHsz";
        String appKey = "pKFyE7QhEN9PCOGIafXwMqmv";
        AVOSCloud.initialize(this, appId, appKey);
        AVAnalytics.enableCrashReport(this, true);
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        LCChatKit.getInstance().init(this, appId, appKey);



        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                // TODO: 17-3-14
                //login check
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });


    }


}
