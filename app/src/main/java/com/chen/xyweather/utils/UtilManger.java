package com.chen.xyweather.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.chen.xyweather.R;

import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chen on 17-4-5.
 * 一些常见操作
 */
public class UtilManger {


    /**
     * 判断是否为今天数据
     *
     * @param date 传入ｄａｔｅ
     * @return true or false
     */
    public static boolean isToday(String date) {
        if (TextUtils.isEmpty(date) || date.length() < 10) {
            return false;
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String today = format.format(new Date());
            if (TextUtils.equals(today, date.substring(0, 10))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void showCreditToast(int layout, Context context) {
        View view = LayoutInflater.from(context).inflate(layout, null);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }


    /**
     * 转换日期2017-4-05为今天、明天、昨天，或者是星期几
     */
    public static String prettyDate(String date) {
        try {
            final String[] strs = date.split("-");
            final int year = Integer.valueOf(strs[0]);
            final int month = Integer.valueOf(strs[1]);
            final int day = Integer.valueOf(strs[2]);
            Calendar c = Calendar.getInstance();
            int curYear = c.get(Calendar.YEAR);
            int curMonth = c.get(Calendar.MONTH) + 1;
            int curDay = c.get(Calendar.DAY_OF_MONTH);
            if (curYear == year && curMonth == month) {
                if (curDay == day) {
                    return "今天";
                } else if ((curDay + 1) == day) {
                    return "明天";
                } else if ((curDay - 1) == day) {
                    return "昨天";
                }
            }
            c.set(year, month - 1, day);
            // http://www.tuicool.com/articles/Avqauq
            // 一周第一天是否为星期天
            boolean isFirstSunday = (c.getFirstDayOfWeek() == Calendar.SUNDAY);
            // 获取周几
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            // 若一周第一天为星期天，则-1
            if (isFirstSunday) {
                dayOfWeek = dayOfWeek - 1;
                if (dayOfWeek == 0) {
                    dayOfWeek = 7;
                }
            }
            // TODO: 17-3-24
            // 打印周几
            // System.out.println(weekDay);

            // 若当天为2014年10月13日（星期一），则打印输出：1
            // 若当天为2014年10月17日（星期五），则打印输出：5
            // 若当天为2014年10月19日（星期日），则打印输出：7
            switch (dayOfWeek) {
                case 1:
                    return "周一";
                case 2:
                    return "周二";
                case 3:
                    return "周三";
                case 4:
                    return "周四";
                case 5:
                    return "周五";
                case 6:
                    return "周六";
                case 7:
                    return "周日";
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 有无网络连接
     */
    public static boolean isNetWorkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 判断是否为ｗｉｆｉ条件
     */
    public static boolean isWifiConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static void handleError(Context context, int error) {
        String tip = "";
        DebugLog.e("error" + error);
        switch (error) {
            case 0:
                tip = context.getString(R.string.network_invalid);
                break;
            case 202:
                tip = context.getString(R.string.phone_occupied);
                break;
            case 210:
                tip = context.getString(R.string.password_wrong);
                break;
            case 214:
                tip = context.getString(R.string.phone_occupied);
                break;
            case 403:
                tip = "没有权限操作";
                break;
            case 502:
                tip = context.getString(R.string.server_maintenance);
                break;
        }
        Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
    }

}
