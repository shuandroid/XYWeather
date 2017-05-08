package com.chen.xyweather.data;

import android.content.Context;

import database.LocalCity;

/**
 * Created by chen on 17-5-8.
 * database manger
 */

public class DBManger {

    public static DBHelper dbHelper;

    public static void insertLocalCity(Context context, String name) {
        LocalCity localCity = new LocalCity();
        localCity.setCity(name);
        insertDB(context, localCity);
    }

    public static void insertDB(Context context, LocalCity localCity) {
        if (dbHelper == null) {
            dbHelper = DBHelper.initHelper(context);

        }
        if (! dbHelper.isSaved(localCity.getCity())) {
            addLocalCity(localCity);
        }

    }

    private static void addLocalCity(LocalCity localCity) {
        dbHelper.insertCity(localCity);
    }

    public static boolean isMapGet(Context context) {
        if (dbHelper == null) {
            dbHelper = DBHelper.initHelper(context);
        }
        if (dbHelper.getCount() > 0) {
            return true;
        }
         return false;
    }
    public static boolean isSaved(Context context, String name) {
        if (dbHelper == null) {
            dbHelper = DBHelper.initHelper(context);
        }

        return dbHelper.isSaved(name);
    }
}
