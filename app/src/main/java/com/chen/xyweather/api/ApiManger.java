package com.chen.xyweather.api;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by chen on 17-3-14.
 * api manger
 */
public class ApiManger {


    static final String URL = "https://free-api.heweather.com/v5/";

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
    static final String API_FIND  = URL + "find";
    static final String API_FINDE = URL + "FIND";



    static OkHttpClient okHttpClient = new OkHttpClient();

    private static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (ApiManger.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient();
                }
            }
        }
        return okHttpClient;
    }


    public interface ApiCallback {
        void onFailure(Throwable t);
        void onResponse(int code , String message);

    }

    static class ApiCallbackWrapper implements ApiCallback {


        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onResponse(int code, String message) {

        }
    }

    static class OkHttpCallbackWrapper implements Callback {


        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

        }
    }

}
