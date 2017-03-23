package com.chen.xyweather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.chen.xyweather.bean.DailyForecast;

import java.util.ArrayList;

/**
 * Created by chen on 17-3-23.
 *
 * 一周天气预报
 * 按文字算高度18行
 * 文字设置为12，高度是216dp
 */
public class DailyForecastView extends View{


    private int height, width;
    private float percent = 0f;
    private final float density;
    private ArrayList<DailyForecast>


    public DailyForecastView(Context context) {
        super(context);
    }

    public DailyForecastView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DailyForecastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
