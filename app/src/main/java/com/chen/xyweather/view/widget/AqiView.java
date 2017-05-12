package com.chen.xyweather.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.chen.xyweather.bean.City;
import com.chen.xyweather.bean.entity.Aqi;
import com.chen.xyweather.ui.activity.MainActivity;

/**
 * Created by chen on 17-3-28.
 * 空气质量的弧形表　10行　120dp
 */
public class AqiView extends View{

    private final float density;
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private RectF rectF = new RectF();
    private City aqiCity;


    public AqiView(Context context, AttributeSet attrs) {
        super(context, attrs);

        density = context.getResources().getDisplayMetrics().density;
        textPaint.setTextAlign(Paint.Align.CENTER);
        if (isInEditMode())
            return;
        textPaint.setTypeface(MainActivity.getTypeface(context));

    }

    @Override
    protected void onDraw(Canvas canvas) {

        float w = getWidth();
        float h = getHeight();
        if (w <= 0 || h <= 0)
            return;
        final float lineSize = h / 10f;

        if (aqiCity == null) {
            textPaint.setStyle(Style.FILL);
            textPaint.setTextSize(lineSize * 1.25f);
            textPaint.setColor(0xaaffffff);
            canvas.drawText("暂无数据", w / 2f, h / 2f, textPaint);
            return;
        }

        float curAqiPercent = -1f;
        try {
            curAqiPercent = Float.valueOf(aqiCity.api) / 500f;
            curAqiPercent = Math.min(curAqiPercent, 1f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        float aqiArcRadius = lineSize * 4f;
        textPaint.setStyle(Style.STROKE);
        textPaint.setStrokeWidth(lineSize * 1);
        textPaint.setColor(0x55ffffff);
        rectF.set(-aqiArcRadius, -aqiArcRadius, aqiArcRadius, aqiArcRadius);
        final int saveCount = canvas.save();
        canvas.translate(w/ 2f, h / 2f);

        float startAngle = -210f;
        float sweepAngle = 240f;
        //全部的弧
        canvas.drawArc(rectF, startAngle + sweepAngle * curAqiPercent, sweepAngle * (1 - curAqiPercent),
                false, textPaint);
        if (curAqiPercent >= 0f) {
            //占用的弧
            textPaint.setColor(0x99ffffff);
            canvas.drawArc(rectF, startAngle, sweepAngle * curAqiPercent, false, textPaint);
            //圆圈
            textPaint.setColor(0xffffffff);
            textPaint.setStrokeWidth(lineSize / 8f);
            canvas.drawCircle(0, 0,lineSize / 3f, textPaint);

            // draw aqi number and text
            textPaint.setStyle(Style.FILL);
            textPaint.setTextSize(lineSize * 1.5f);
            textPaint.setColor(0xffffffff);

            try {
                canvas.drawText(aqiCity.api + "", 0, lineSize * 3, textPaint);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //文字，质量，优、中…
            textPaint.setTextSize(lineSize * 1f);
            textPaint.setColor(0x88ffffff);
            try {
                canvas.drawText(aqiCity.qlty + "", 0, lineSize * 4.25f, textPaint);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // draw the aqi line
            canvas.rotate(startAngle + sweepAngle * curAqiPercent - 180f);
            textPaint.setStyle(Style.STROKE);
            textPaint.setColor(0xffffffff);
            float startX = lineSize / 3f;
            canvas.drawLine(-startX, 0, -lineSize * 4.5f, 0, textPaint);

        }
        canvas.restoreToCount(saveCount);

    }

    public void setData(Aqi aqi) {

        if (aqi != null && aqi.city !=null) {
            this.aqiCity = aqi.city;
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }
}
