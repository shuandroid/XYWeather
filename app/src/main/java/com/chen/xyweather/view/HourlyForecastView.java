package com.chen.xyweather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.chen.xyweather.bean.HourlyForecast;
import com.chen.xyweather.bean.Weather;
import com.chen.xyweather.ui.activity.MainActivity;
import com.chen.xyweather.utils.UtilManger;

import java.util.ArrayList;

/**
 * Created by chen on 17-3-27.
 * 24h天气
 * 12行
 * 12 * 12 = 144dp
 */
public class HourlyForecastView extends View {

    private int height, width;
    private final float density;

    private ArrayList<HourlyForecast> forecastList;
    private Path tmpPath = new Path();
    private Path goneTmpPath = new Path();

    private Data[] datas;

    private final int fullDataCount = 9;
    private final DashPathEffect dashPathEffect;

    private final TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    public HourlyForecastView(Context context, AttributeSet attrs) {
        super(context, attrs);

        density = context.getResources().getDisplayMetrics().density;
        dashPathEffect = new DashPathEffect(new float[]{density * 3, density * 3}, 1);
        if (isInEditMode())
            return;
        init(context);
    }

    /**
     * 初始化paint
     *
     * @param context context
     */
    private void init(Context context) {
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1f * density);
        paint.setTextSize(12f * density);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(MainActivity.getTypeface(context));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (isInEditMode())
            return;
        paint.setStyle(Paint.Style.FILL);
        final float textSize = this.height / 12f;
        paint.setTextSize(textSize);

        final float textOffset = getTextPaintOffset(paint);
        final float dH = textSize * 4f;
        final float dCenterY = textSize * 4f;

        //没有数据时
        if (datas == null || datas.length <= 1) {
            canvas.drawLine(0, dCenterY, width, dCenterY, paint);
            return;
        }

        final float dW = width * 1f / fullDataCount;
        tmpPath.reset();
        goneTmpPath.reset();

        final int length = datas.length;
        float[] x = new float[length];
        float[] y = new float[length];

        final float textPercent = 1f;
        final float pathPercent = 1f;

        final float smallerHeight = 4 * textSize;
        final float smallerPercent = 1 - smallerHeight / 2f / dH;
        paint.setAlpha((int) (255 * textPercent));

        //没有的数据，要跳过的距离
        final int dataLengthOffset = Math.max(0, fullDataCount - length);

        for (int i = 0; i < length; i++) {

            final Data d = datas[i];
            final int index = i + dataLengthOffset;
            x[i] = index * dW + dW / 2f;
            //每个高低值的偏差
            y[i] = dCenterY - d.offsetPercent * dH * smallerPercent;

            canvas.drawText(d.tmp + "°", x[i], y[i] - textSize + textOffset, paint);

            //
            if (i == 0) {
                final float i0_x = dW / 2f;
                canvas.drawText("时间", i0_x, textSize * 7.5f + textOffset, paint);
                canvas.drawText("降水率", i0_x, textSize * 9f + textOffset, paint);
                canvas.drawText("风力", i0_x, textSize * 10.5f + textOffset, paint);

            }

            //分别是 时间、降水率、风力
            canvas.drawText(d.date.substring(11), x[i], textSize * 7.5f + textOffset, paint);
            canvas.drawText(d.pop + "%", x[i], textSize * 9f + textOffset, paint);
            canvas.drawText(d.wind_sc, x[i], textSize * 10.5f + textOffset, paint);
        }

        paint.setAlpha(255);
        paint.setStyle(Paint.Style.STROKE);

        final float data_x0 = dataLengthOffset * dW;
        //画温度线
        goneTmpPath.moveTo(0, y[0]);
        //跨过没有的数据部分
        goneTmpPath.lineTo(data_x0, y[0]);
        paint.setPathEffect(dashPathEffect);
        canvas.drawPath(goneTmpPath, paint);

        for (int i = 0; i < length - 1; i++) {
            float midX = (x[i] + x[i + 1]) / 2f;
            float midY = (y[i] + y[i + 1]) / 2f;
            if (i == 0)
                tmpPath.moveTo(data_x0, y[i]);
            tmpPath.cubicTo(x[i] - 1, y[i], x[i], y[i], midX, midY);
            if (i == (length - 2)) {
                tmpPath.cubicTo(x[i + 1] - 1, y[i + 1], x[i + 1], y[i + 1], this.width, y[i + 1]);
            }
        }

        final boolean needClip = pathPercent < 1f;

        // TODO: 17-3-27 可不用？
        if (needClip) {
            canvas.save();
            canvas.clipRect(0, 0, width * pathPercent, height);
        }

        paint.setPathEffect(null);
        canvas.drawPath(tmpPath, paint);
        if (needClip)
            canvas.restore();

    }


    public void setData(Weather weather) {

        if (weather == null || !weather.isOk()) {
            return;
        }

        if (forecastList == weather.get().hourlyForecasts) {
            invalidate();
            return;
        }

        try {
            final ArrayList<HourlyForecast> wHourlyForecast = weather.get().hourlyForecasts;
            if (wHourlyForecast.size() == 0) {
                return;
            }

            if (!UtilManger.isToday(wHourlyForecast.get(0).date)) {
                return;
            }

            this.forecastList = weather.get().hourlyForecasts;
            if (forecastList == null || forecastList.size() == 0) {
                return;
            }

            datas = new Data[forecastList.size()];

            int allMax = Integer.MIN_VALUE;
            int allMin = Integer.MAX_VALUE;
            for (int i = 0; i < forecastList.size(); i++) {
                HourlyForecast hourlyForecast = forecastList.get(i);

                int tmp = Integer.valueOf(hourlyForecast.tmp);
                if (allMax < tmp) {
                    allMax = tmp;
                }
                if (allMin > tmp)
                    allMin = tmp;
                final Data data = new Data();
                data.tmp = tmp;
                data.date = hourlyForecast.date;
                data.pop = hourlyForecast.pop;
                data.wind_sc = hourlyForecast.wind.sc;
                datas[i] = data;
            }
            //差值与平均值
            float allDistance = Math.abs(allMax - allMin);
            float averageDistance = (allMax + allMin) / 2f;

            //偏移值
            for (Data data : datas) {
                data.offsetPercent = (data.tmp - averageDistance) / allDistance;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        invalidate();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    public static float getTextPaintOffset(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return -(fontMetrics.bottom - fontMetrics.top) / 2f - fontMetrics.top;
    }

    //    class
    public class Data {

        /**
         * 温度偏差值， 温度，日期，风，降水概率
         */
        public float offsetPercent;
        public int tmp;
        public String date;
        public String wind_sc;
        public String pop;

    }


}
