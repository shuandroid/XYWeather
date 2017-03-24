package com.chen.xyweather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.chen.xyweather.api.ApiManger;
import com.chen.xyweather.bean.DailyForecast;
import com.chen.xyweather.bean.Weather;
import com.chen.xyweather.ui.MainActivity;

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
    private ArrayList<DailyForecast> forecastList;

    //最低最高气温的两条线
    private Path tmpMaxPath = new Path();
    private Path tmpMinPath = new Path();

    private Data[] datas;
    //消除锯齿
    private final TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);


    public DailyForecastView(Context context, AttributeSet attrs) {
        super(context, attrs);

        density = context.getResources().getDisplayMetrics().density;
        if (isInEditMode()) {
            return;
        }
        init(context);

    }

    private void init(Context context) {
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1f * density);
        paint.setTextSize(12f * density);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(MainActivity.getTypeface(context));

    }

    public void resetAnimation() {
        percent = 0f;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode()) {
            return;
        }
        paint.setStyle(Paint.Style.FILL);

        //一共需要 顶部文字2(+图占8行)+底部文字2 + 【间距1 + 日期1 + 间距0.5 +　晴1 + 间距0.5f + 微风1 + 底部边距1f 】 = 18行
        //                                  12     13       14      14.5    15.5      16      17       18
        final float textSize = this.height / 18f;
        paint.setTextSize(textSize);
        final float textOffset = getTextPaintOffset(paint);
        final float dH = textSize * 8f;
        final float dCenterY = textSize * 6f ;

        //没有数据的情况下只画一条线
        if (datas == null || datas.length <= 1) {
            canvas.drawLine(0, dCenterY, this.width, dCenterY, paint);
            return;
        }

        final float dW = this.width * 1f / datas.length;

        tmpMaxPath.reset();
        tmpMinPath.reset();

        final int length = datas.length;
        //三组数据
        float[] x = new float[length];
        float[] yMax = new float[length];
        float[] yMin = new float[length];

        final float textPercent = (percent >= 0.6f) ? (percent - 0.6f) / 0.4f : 0f;
        final float paintPercent = (percent >= 0.6f) ? 1f : (percent / 0.6f);

        paint.setAlpha((int) (255 * textPercent));
        for (int i = 0; i < length; i++) {
            final Data d = datas[i];
            x[i] = i * dW + dW /2f;
            yMax[i] = dCenterY - d.maxOffsetPercent * dH;
            yMin[i] = dCenterY - d.minOffsetPercent * dH;

            canvas.drawText(d.tmp_max + "°", x[i], yMax[i] - textSize + textOffset, paint);
            canvas.drawText(d.tmp_min + "°", x[i], yMin[i] + textSize + textOffset, paint);
            //日期d.date.substring(5)
            canvas.drawText(ApiManger.prettyDate(d.date),x[i], textSize * 13.5f + textOffset, paint);
            //晴, 天气的描述
            canvas.drawText(d.cond_txt_d + "", x[i], textSize * 15f + textOffset, paint);
            //风
            canvas.drawText(d.wind_sc, x[i], textSize * 15f + textOffset, paint);

        }

        paint.setAlpha(255);

        for (int i = 0; i < (length - 1); i++) {
            final float midX = (x[i] + x[i + 1]) / 2f;
            final float midYMax = (yMax[i] + yMax[i + 1]) / 2f;
            final float midYMin = (yMin[i] + yMin[i + 1]) / 2f;
            if(i == 0){
                tmpMaxPath.moveTo(0, yMax[i]);
                tmpMinPath.moveTo(0, yMin[i]);
            }
            //
            tmpMaxPath.cubicTo(x[i]-1, yMax[i],x[i], yMax[i], midX, midYMax);
            tmpMinPath.cubicTo(x[i]-1, yMin[i],x[i], yMin[i], midX, midYMin);

            if(i == (length - 2)){
                tmpMaxPath.cubicTo(x[i + 1]-1, yMax[i + 1], x[i + 1], yMax[i + 1], this.width, yMax[i + 1]);
                tmpMinPath.cubicTo(x[i + 1]-1, yMin[i + 1], x[i + 1], yMin[i + 1], this.width, yMin[i + 1]);
            }
        }
        paint.setStyle(Paint.Style.STROKE);
        final boolean needClip = paintPercent < 1f;
        if (needClip) {
            canvas.save();
            canvas.clipRect(0, 0, this.width * paintPercent, this.height);

        }
        canvas.drawPath(tmpMaxPath, paint);
        canvas.drawPath(tmpMinPath, paint);
        if (needClip) {
            canvas.restore();
        }
        if (percent < 1) {
            percent += 0.025f;
            percent = Math.min(percent, 1f);
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }

    /**
     * 各属性 是为了与 dailyForecast 里的属性相契合
     */
    public class Data {

        public float minOffsetPercent, maxOffsetPercent;// 差值%
        public int tmp_max, tmp_min;
        public String date;
        public String wind_sc;
        public String cond_txt_d;
    }




    /**
     * 获得的数据
     * @param weather 天气
     */
    public void setData(Weather weather) {

        if (weather == null || !weather.isOk()) {
            return;
        }

        if (this.forecastList == weather.get().dailyForecasts) {
            percent = 0f;
            invalidate();
            return;
        }

        forecastList = weather.get().dailyForecasts;
        if (forecastList == null || forecastList.size() == 0)  {
            return;
        }

        datas = new Data[forecastList.size()];

        //
        try {
            int allMin = Integer.MAX_VALUE;
            int allMax = Integer.MIN_VALUE;
            for (int i = 0; i < forecastList.size(); i++) {
                DailyForecast forecast = forecastList.get(i);
                int min = Integer.valueOf(forecast.tmp.min);
                int max = Integer.valueOf(forecast.tmp.max);
                if (allMax < max)
                    allMax = max;
                if (allMin > min)
                    allMin = min;
                final Data data = new Data();
                data.tmp_min = min;
                data.tmp_max = max;
                data.date = forecast.date;
                data.wind_sc = forecast.wind.sc;
                data.cond_txt_d = forecast.cond.txt_d;
                datas[i] = data;

            }

            //后面绘制
            float allDistance = Math.abs(allMax - allMin);
            float averageDistance = (allMax + allMin) /2f;
            for (Data data :datas) {
                data.maxOffsetPercent = (data.tmp_max - averageDistance) /allDistance;
                data.minOffsetPercent = (data.tmp_min - averageDistance) /allDistance;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        percent = 0f;
        invalidate();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
    }

    /**
     * 获取
     */
    public static float getTextPaintOffset(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return -(fontMetrics.bottom - fontMetrics.top) / 2f - fontMetrics.top;
    }

}
