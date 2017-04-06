package com.chen.xyweather.api;

import android.provider.Settings;
import android.text.TextUtils;

import okhttp3.Callback;
import okhttp3.Request;
import com.chen.xyweather.api.ApiManger;
import com.chen.xyweather.utils.DebugLog;

import static com.chen.xyweather.api.ApiManger.CLIENT;
/**
 * Created by chen on 17-3-20.
 *
 */
public class Weather {

    /**
     * 正确API使用
     */
    static final String API_WEATHER = ApiManger.URL + "weather";

    public interface WeatherApiCallback extends ApiManger.ApiCallback {

    }

    public  void searchWeatherByCity(String city, WeatherApiCallback weatherApiCallback) {

        if (TextUtils.isEmpty(city)) {
            throw new IllegalArgumentException("City shouldn't be null or empty!");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("?").append("city=").append(city);
        sb.append("&").append("key=").append(ApiManger.KEY);
        Request request = new Request.Builder().url(API_WEATHER + sb.toString()).get().build();

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




    private void test(){

    }
    //test
//    public static void main(String  args[]) {
//        System.out.println("debug->message----->");
//
//        Weather.searchWeatherByCity("武汉", new WeatherApiCallback() {
//            @Override
//            public void onFailure(Throwable t) {
//
//                DebugLog.e("throwable" + t);
//            }
//
//            @Override
//            public void onResponse(int code, String message) {
//                DebugLog.e("debug->message----->" + message);
//                System.out.println("debug->message----->" + message);
//            }
//        });
//
//        System.out.println("debug->message--last--->");
//
//    }

}
