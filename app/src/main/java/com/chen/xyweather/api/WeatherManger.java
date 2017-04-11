package com.chen.xyweather.api;

import android.provider.Settings;
import android.text.TextUtils;

import okhttp3.Callback;
import okhttp3.Request;
import com.chen.xyweather.api.ApiManger;
import com.chen.xyweather.bean.Weather;
import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.view.drawer.BaseDrawer;
import com.chen.xyweather.view.drawer.BaseDrawer.Type;


import static com.chen.xyweather.api.ApiManger.CLIENT;
/**
 * Created by chen on 17-3-20.
 *
 */
public class WeatherManger {

    /**
     * 正确API使用
     */
    static final String API_WEATHER = ApiManger.URL + "weather";
    static final String API_FORECAST = ApiManger.URL + "forecast";

    public interface WeatherApiCallback extends ApiManger.ApiCallback {

    }


    public interface ApiListener {
        public void onReceiveWeather(Weather weather, boolean updated);

        public void onUpdateError();
    }

    public static void searchWeatherByCity(String city) {
        if (TextUtils.isEmpty(city)) {
            throw new IllegalArgumentException("City shouldn't be null or empty!");
        }

    }

    public  static void searchWeatherByCity(String city, WeatherApiCallback weatherApiCallback) {

        if (TextUtils.isEmpty(city)) {
            throw new IllegalArgumentException("City shouldn't be null or empty!");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("?").append("city=").append(city);
        sb.append("&").append("key=").append(ApiManger.KEY);

        Request request = new Request.Builder().url(API_WEATHER + sb.toString()).get().build();

        executeRequest(request, weatherApiCallback);

    }


    public static void searchMoreDailyForcest(String city, WeatherApiCallback weatherApiCallback) {
        if (TextUtils.isEmpty(city)) {
            throw new IllegalArgumentException("City shouldn't be null or empty!");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("?").append("city=").append(city);
        sb.append("&").append("key=").append(ApiManger.KEY);

        Request request = new Request.Builder().url(API_FORECAST + sb.toString()).get().build();

        executeRequest(request, weatherApiCallback);
    }

    /**
     * 把请求加入队列，调度
     * @param request 请求
     * @param weatherApiCallback 调用请求
     */
    private static void executeRequest(Request request, final WeatherApiCallback weatherApiCallback) {
        CLIENT.newCall(request).enqueue(new ApiManger.OkHttpCallbackWrapper(weatherApiCallback));
    }



    public static Type convertWeatherType(Weather weather) {
        if (weather == null || !weather.isOk()) {
            return Type.DEFAULT;
        }


        return Type.DEFAULT;
    }


}
