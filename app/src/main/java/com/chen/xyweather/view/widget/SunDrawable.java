package com.chen.xyweather.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.TypedValue;

/**
 * Created by chen on 17-3-22.
 * 晴天 绘制draw
 */
public class SunDrawable extends RefreshDrawable {


    RectF mBounds;
    float mWidth;
    float mHeight;
    float mCenterX;
    float mCenterY;
    float mPercent;
    final float mMaxAngle = 180f;
    final float mRadius;
    final Paint mPaint = new Paint();
    //偏移量，是否运行，角度
    int mOffset;
    boolean mRunning;
    float mDegrees;


    public SunDrawable(Context context, PullRefreshLayout layout) {
        super(context, layout);

        mPaint.setAntiAlias(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(dp2px(2));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xffffffff);

        mRadius = dp2px(6);

    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        mHeight = getPullRefreshLayout().getFinalOffset();
        mWidth = mHeight;
        mBounds = new RectF(bounds.width() / 2 - mWidth / 2, bounds.top - mHeight / 2, bounds.width() / 2 + mWidth / 2, bounds.top + mHeight / 2);
        mCenterX = mBounds.centerX();
        mCenterY = mBounds.centerY();
    }

    @Override
    public void setPercent(float percent) {

        mPercent = percent;
        invalidateSelf();
    }

    @Override
    public void setColorSchemeColors(int[] colorSchemeColors) {

    }

    @Override
    public void offsetTopAndBottom(int offset) {

        mOffset += offset;
        invalidateSelf();
    }

    @Override
    public void start() {

        mRunning = true;
        mDegrees = 0;
        invalidateSelf();
    }

    @Override
    public void stop() {

        mRunning = false;
    }

    @Override
    public boolean isRunning() {

        return mRunning;
    }

    /**
     * 重绘
     *
     * @param canvas canvas
     */
    @Override
    public void draw(Canvas canvas) {

        canvas.save();
        canvas.translate(0, mOffset / 2);

        if (isRunning()) {
            canvas.rotate(mDegrees, mCenterX, mCenterY);
            mDegrees = mDegrees < 360 ? mDegrees + 8 : 0;
            invalidateSelf();
        }
        // TODO: 17-3-22 需要进一步设置
        float percent = mPercent;

        //画椭圆
        RectF oval = new RectF(mCenterX - mRadius, mCenterY - mRadius, mCenterY + mRadius, mCenterY + mRadius);
        canvas.drawArc(oval, 180, mMaxAngle * percent, false, mPaint);

        canvas.drawArc(oval, 0, mMaxAngle * percent, false, mPaint);

        final int lightCount = 8;
        mPaint.setAlpha((int) (255f * percent));

        //画刻度
        for (int i = 0; i < lightCount; i++) {
            double radians = Math.toRadians(i * (360 / lightCount));
            float x1 = (float) (Math.cos(radians) * mRadius * 1.6f);
            float y1 = (float) (Math.sin(radians) * mRadius * 1.6f);
            float x2 = x1 * (1f + 0.4f * percent);// 0.7*0.857=0.6 也就是说刻度盘占 0.6~0.7的部分
            float y2 = y1 * (1f + 0.4f * percent);
            canvas.drawLine(mCenterX + x1, y1, mCenterX + x2, y2, mPaint);
        }
        mPaint.setAlpha(255);

        canvas.restore();

    }

    /**
     * dp 转化为px
     *
     * @param dp dp
     * @return px
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, getContext().getResources().getDisplayMetrics());
    }

}
