package com.chen.xyweather.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.chen.xyweather.api.ApiManger;
import com.chen.xyweather.bean.DailyForecast;
import com.chen.xyweather.bean.Weather;
import com.chen.xyweather.bean.entity.Now;
import com.chen.xyweather.ui.MainActivity;
import com.chen.xyweather.utils.UiUtil;

/**
 * Created by chen on 17-3-28.
 * 画太阳和风
 */
public class AstroView  extends View{

    private int width, height;
    private final float density;
    private final DashPathEffect dashPathEffect;

    private Path sunPath = new Path();
    private RectF sunRectF = new RectF();
    private Path fanPath = new Path();// 旋转的风扇的扇叶
    private Path fanPillarPath = new Path();// 旋转的风扇的柱子
    private float fanPillerHeight;

    private final TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private float paintTextOffset;
    final float offsetDegree = 15f;
    private float curRotate;// 旋转的风扇的角度
    private DailyForecast todayForecast;
    private Now now;

    private float sunArcHeight, sunArcRadius;

    private Rect visibleRect = new Rect();



    public AstroView(Context context, AttributeSet attrs) {
        super(context, attrs);
        density = context.getResources().getDisplayMetrics().density;
        dashPathEffect = new DashPathEffect(new float[] { density * 3, density * 3 }, 1);
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(density);
        textPaint.setTextAlign(Paint.Align.CENTER);
        if (isInEditMode())
            return;
        textPaint.setTypeface(MainActivity.getTypeface(context));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public void setData(Weather weather) {

        try {
            if (ApiManger.acceptWeather(weather)) {
                now = weather.get().now;
                final DailyForecast forecast = weather.getDailyForecast();
                if (forecast != null) {
                    todayForecast = forecast;
                }

                if (now != null || todayForecast != null) {
                    invalidate();
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 间距1 图8.5行 间距0.5 字1行 间距1 = 12;
     *
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;

        final float textSize = height / 12f;
        textPaint.setTextSize(textSize);
        paintTextOffset = UiUtil.getTextPaintOffset(textPaint);

        sunPath.reset();

        sunArcHeight = textSize * 8.5f;
        sunArcRadius = (float) (sunArcHeight / (1f - Math.sin(Math.toRadians(offsetDegree))));
        final float sunArcLeft = width / 2f - sunArcRadius;
        sunRectF.left = sunArcLeft;
        sunRectF.top = textSize;
        sunRectF.right = width - sunArcLeft;
        sunRectF.bottom = sunArcRadius * 2f + textSize;
        sunPath.addArc(sunRectF, -165, +150);// 圆形的最右端点为0，顺时针sweepAngle

        // fanPath和fanPillarPath的中心点在扇叶圆形的中间
        fanPath.reset();
        final float fanSize = textSize * 0.2f;// 风扇底部半圆的半径
        final float fanHeight = textSize * 2f;
        final float fanCenterOffsetY = fanSize * 1.6f;

        // fanPath.moveTo(fanSize, -fanCenterOffsetY);
        // 也可以用arcTo
        // 从右边到底部到左边了的弧
        fanPath.addArc(new RectF(-fanSize, -fanSize - fanCenterOffsetY, fanSize, fanSize - fanCenterOffsetY), 0,
                180);
        // fanPath.lineTo(0, -fanHeight - fanCenterOffsetY); 贝塞尔曲线
        fanPath.quadTo(-fanSize * 1f, -fanHeight * 0.5f - fanCenterOffsetY, 0, -fanHeight - fanCenterOffsetY);
        fanPath.quadTo(fanSize * 1f, -fanHeight * 0.5f - fanCenterOffsetY, fanSize, -fanCenterOffsetY);
        fanPath.close();



    }
}
