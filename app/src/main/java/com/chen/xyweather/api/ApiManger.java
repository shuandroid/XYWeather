package com.chen.xyweather.api;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import com.chen.xyweather.bean.DailyForecast;
import com.chen.xyweather.bean.Weather;
import com.chen.xyweather.view.drawer.BaseDrawer;
import com.chen.xyweather.view.drawer.BaseDrawer.Type;


/**
 * Created by chen on 17-3-14.
 * aqi manger
 */
public class ApiManger {


    static final String URL = "https://free-aqi.heweather.com/v5/";

    static final String KEY = "2af7a47e6e8342ba928c6fff2a569e8e";

    static final String API = URL + KEY;

    static final String API_SEVEN = URL + "forecast";

    static final String API_NOW = URL + "now";
    static final String API_HOUR = URL + "hourly";
    static final String API_LIFE = URL + "suggestion";
    static final String API_ALARM = URL + "alarm";
    static final String API_WEATHER = URL + "weather";
    static final String API_SCENIC = URL + "scenic";
    static final String API_HISTORY = URL + "historical";
    static final String API_SEARCH = URL + "search";
    static final String API_FIND = URL + "find";
    static final String API_FINDE = URL + "FIND";


    static final OkHttpClient CLIENT = new OkHttpClient();


    public interface ApiCallback {
        void onFailure(Throwable t);

        void onResponse(int code, String message);

    }

    static class ApiCallbackWrapper implements ApiCallback {

        final WeakReference<ApiCallback> apiCallbackWeakReference;

        public ApiCallbackWrapper(ApiCallback apiCallback) {
            apiCallbackWeakReference = new WeakReference<>(apiCallback);
        }

        @Override
        public void onFailure(Throwable t) {
            ApiCallback apiCallback = ApiCallbackWrapper.this.apiCallbackWeakReference.get();
            if (null != apiCallback) {
                UI_HANDLER.handleFailure(apiCallback, t);
            }
        }

        @Override
        public void onResponse(int code, String message) {

            ApiCallback apiCallback = ApiCallbackWrapper.this.apiCallbackWeakReference.get();
            if (null != apiCallback) {
                UI_HANDLER.handleResponse(apiCallback, code, message);
            }
        }
    }

    static class OkHttpCallbackWrapper implements Callback {

        final WeakReference<ApiCallback> apiCallbackWeakReference;

        public OkHttpCallbackWrapper(ApiCallback apiCallback) {
            this.apiCallbackWeakReference = new WeakReference<>(apiCallback);
        }

        @Override
        public void onFailure(Call call, IOException e) {
            ApiCallback apiCallback = OkHttpCallbackWrapper.this.apiCallbackWeakReference.get();
            if (apiCallback != null) {
                UI_HANDLER.handleFailure(apiCallback, e);
            }

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            ApiCallback apiCallback = OkHttpCallbackWrapper.this.apiCallbackWeakReference.get();
            if (null != apiCallback) {
                UI_HANDLER.handleResponse(
                        apiCallback, response.code(), response.body().toString()
                );
            }

        }
    }

    //主线程
    static final APiUIHandler UI_HANDLER = new APiUIHandler(Looper.getMainLooper());

    static final class APiUIHandler extends Handler {

        static final class MessageWrapper {

            WeakReference<ApiCallback> cbReference;
            Throwable throwable;
            int statusCode;
            int postType;
            String message;

            public MessageWrapper(ApiCallback callback, Throwable throwable,
                                  int statusCode, int postType, String message) {
                MessageWrapper.this.cbReference = new WeakReference<ApiCallback>(callback);
                MessageWrapper.this.throwable = throwable;
                MessageWrapper.this.statusCode = statusCode;
                MessageWrapper.this.postType = postType;
                MessageWrapper.this.message = message;
            }
        }


        static final class WSMessageWrapper {
        }

        static final int MESSAGE_POST_THROWABLE = 1;

        static final int MESSAGE_POST_RESPONSE = 1 << 1;

        static final int MESSAGE_POST_WS_THROWABLE = 1 << 2;

        public APiUIHandler(Looper looper) {
            super(looper);
        }


        public void handleFailure(ApiCallback callback, Throwable t) {
            if (null != callback) {
                MessageWrapper wrapper = new MessageWrapper(callback, t, -1,
                        MESSAGE_POST_THROWABLE, null);
                Message message = APiUIHandler.this.obtainMessage(MESSAGE_POST_THROWABLE, wrapper);
                message.sendToTarget();
            }
        }

        public void handleResponse(ApiCallback callback, int code, String message) {
            if (null != callback) {
                MessageWrapper wrapper = new MessageWrapper(callback, null, code,
                        MESSAGE_POST_RESPONSE, message);
                Message postMessage = APiUIHandler.this.obtainMessage(MESSAGE_POST_RESPONSE, wrapper);
                postMessage.sendToTarget();
            }
        }


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_POST_THROWABLE: {
                    performFailure((MessageWrapper) msg.obj);
                }
                break;

                case MESSAGE_POST_RESPONSE: {
                    performResponse((MessageWrapper) msg.obj);
                }
                break;
                default:
                    break;

            }

        }

        private void performFailure(MessageWrapper wrapper) {
            ApiCallback callback = wrapper.cbReference.get();
            Throwable throwable = wrapper.throwable;
            if (null != callback) {
                callback.onFailure(throwable);
            }
        }

        private void performResponse(MessageWrapper wrapper) {

            ApiCallback callback = wrapper.cbReference.get();
            int statusCode = wrapper.statusCode;
            String message = wrapper.message;
            if (null != callback) {
                callback.onResponse(statusCode, message);
            }
        }
    }


    /**
     * 是否是合法的Weather数据
     *
     * @param weather
     * @return
     */
    public static boolean acceptWeather(Weather weather) {
        if (weather == null || !weather.isOk()) {
            return false;
        }
        return true;
    }

    /**
     * 把Weather转换为对应的BaseDrawer.Type
     * @param weather weather
     */
    public static BaseDrawer.Type convertWeatherType(Weather weather) {
        if (weather == null || !weather.isOk()) {
            return Type.DEFAULT;
        }
        final boolean isNight = isNight(weather);
        try {
            final int w = Integer.valueOf(weather.get().now.cond_.code);
            switch (w) {
                case 100:
                    return isNight ? Type.CLEAR_N : Type.CLEAR_D;
                case 101:// 多云
                case 102:// 少云
                case 103:// 晴间多云
                    return isNight ? Type.CLOUDY_N : Type.CLOUDY_D;
                case 104:// 阴
                    return isNight ? Type.OVERCAST_N : Type.OVERCAST_D;
                // 200 - 213是风
                case 200:
                case 201:
                case 202:
                case 203:
                case 204:
                case 205:
                case 206:
                case 207:
                case 208:
                case 209:
                case 210:
                case 211:
                case 212:
                case 213:
                    return isNight ? Type.WIND_N : Type.WIND_D;
                case 300:// 阵雨Shower Rain
                case 301:// 强阵雨 Heavy Shower Rain
                case 302:// 雷阵雨 Thundershower
                case 303:// 强雷阵雨 Heavy Thunderstorm
                case 304:// 雷阵雨伴有冰雹 Hail
                case 305:// 小雨 Light Rain
                case 306:// 中雨 Moderate Rain
                case 307:// 大雨 Heavy Rain
                case 308:// 极端降雨 Extreme Rain
                case 309:// 毛毛雨/细雨 Drizzle Rain
                case 310:// 暴雨 Storm
                case 311:// 大暴雨 Heavy Storm
                case 312:// 特大暴雨 Severe Storm
                case 313:// 冻雨 Freezing Rain
                    return isNight ? Type.RAIN_N : Type.RAIN_D;
                case 400:// 小雪 Light Snow
                case 401:// 中雪 Moderate Snow
                case 402:// 大雪 Heavy Snow
                case 403:// 暴雪 Snowstorm
                case 407:// 阵雪 Snow Flurry
                    return isNight ? Type.SNOW_N : Type.SNOW_D;
                case 404:// 雨夹雪 Sleet
                case 405:// 雨雪天气 Rain And Snow
                case 406:// 阵雨夹雪 Shower Snow
                    return isNight ? Type.RAIN_SNOW_N : Type.RAIN_SNOW_D;
                case 500:// 薄雾
                case 501:// 雾
                    return isNight ? Type.FOG_N : Type.FOG_D;
                case 502:// 霾
                case 504:// 浮尘
                    return isNight ? Type.HAZE_N : Type.HAZE_D;
                case 503:// 扬沙
                case 506:// 火山灰
                case 507:// 沙尘暴
                case 508:// 强沙尘暴
                    return isNight ? Type.SAND_N : Type.SAND_D;
                default:
                    return isNight ? Type.UNKNOWN_N : Type.UNKNOWN_D;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isNight ? Type.UNKNOWN_N : Type.UNKNOWN_D;
    }

// TODO: 17-3-28 检查

    public static boolean isNight(Weather weather) {
        if (weather == null || !weather.isOk()) {
            return false;
        }
        // SimpleDateFormat time=new SimpleDateFormat("yyyy MM dd HH mm ss");
        try {
            final Date date = new Date();
            String todaydate = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
            String todaydate1 = (new SimpleDateFormat("yyyy-M-d")).format(date);
            DailyForecast todayForecast = null;
            for (DailyForecast forecast : weather.get().dailyForecasts) {
                if (TextUtils.equals(todaydate, forecast.date) || TextUtils.equals(todaydate1, forecast.date)) {
                    todayForecast = forecast;
                    break;
                }
            }
            if (todayForecast != null) {
                final int curTime = Integer.valueOf((new SimpleDateFormat("HHmm").format(date)));
                final int srTime = Integer.valueOf(todayForecast.astro.sr.replaceAll(":", ""));// 日出
                final int ssTime = Integer.valueOf(todayForecast.astro.ss.replaceAll(":", ""));// 日落
                if (curTime > srTime && curTime <= ssTime) {// 是白天
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
