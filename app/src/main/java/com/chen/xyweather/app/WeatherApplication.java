package com.chen.xyweather.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by chen on 17-3-14.
 *
 */
public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

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
