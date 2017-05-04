package com.chen.xyweather.model;

/**
 * Created by chen on 17-5-4.
 * 单例
 */
public class UserInstance {

    public static volatile UserHelper mHelper = null;

    public static UserHelper getInstance() {
        if (mHelper == null) {
            synchronized (UserInstance.class) {
                if (mHelper == null) {
                    mHelper = new UserHelper();

                }
            }
        }
        return mHelper;
    }
}
