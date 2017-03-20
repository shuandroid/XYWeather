package com.chen.xyweather.api;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.lang.ref.WeakReference;

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

}
