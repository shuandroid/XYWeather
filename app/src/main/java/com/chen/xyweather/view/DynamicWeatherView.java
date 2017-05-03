package com.chen.xyweather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AnimationUtils;

import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.view.drawer.BaseDrawer;

import com.chen.xyweather.view.drawer.BaseDrawer.Type;

/**
 * Created by chen on 17-3-13.
 * 天气主界面 参考
 */
public class DynamicWeatherView extends SurfaceView implements SurfaceHolder.Callback {

    //反射
    static final String TAG = DynamicWeatherView.class.getSimpleName();

    private DrawThread mDrawThread;

    private BaseDrawer preDrawer, curDrawer;
    private float curDrawerAlpha = 0f;
    private Type curType = Type.DEFAULT;
    private int mWidth, mHeight;

    public DynamicWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }


    private void init(Context context) {
        curDrawerAlpha = 0f;
        mDrawThread = new DrawThread();
        final SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        // TODO: 17-4-11
//        surfaceHolder.setFormat(PixelFormat.RGBA_8888);
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mDrawThread.start();
    }

    private void setDrawer(BaseDrawer baseDrawer) {
        if (baseDrawer == null) {
            DebugLog.e("drawer_  null");
            return;
        }
        //透明度
        curDrawerAlpha = 0f;
        if (this.curDrawer != null) {
            this.preDrawer = curDrawer;
        }
        this.curDrawer = baseDrawer;

    }

    public void setDrawerType(Type type) {
        if (type == null) {
            DebugLog.e("drawer_ type null");
            return;
        }
        if (type != curType) {
            DebugLog.e("view--->");
            curType = type;
            setDrawer(BaseDrawer.makeDrawerByType(getContext(), curType));
        } else {
            DebugLog.e("cur type " + curType);
        }
    }

    //状态变化
    public void onResume() {

        synchronized (mDrawThread) {
            mDrawThread.mRunning = true;
            mDrawThread.notify();
        }
        DebugLog.i("onResume");
    }

    public void onPause() {
        synchronized (mDrawThread) {
            mDrawThread.mRunning = false;
            mDrawThread.notify();
        }
    }

    public void onDestroy() {
        synchronized (mDrawThread) {
            mDrawThread.mQuit = true;
            mDrawThread.notify();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        synchronized (mDrawThread) {
            mDrawThread.mSurface = holder;
            mDrawThread.notify();
        }

        DebugLog.i("surface created success");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        synchronized (mDrawThread) {
            mDrawThread.mSurface = holder;
            mDrawThread.notify();
            while (mDrawThread.mActive) {
                try {
                    mDrawThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        holder.removeCallback(this);
        DebugLog.i("surface destroy");
    }


    private boolean drawSurface(Canvas canvas) {

        final int w = mWidth;
        final int h = mHeight;
        if (w == 0 || 0 == h)
            return true;

        boolean needDrawNextFrame = false;

        if (curDrawer != null) {
            curDrawer.setSize(w, h);
            needDrawNextFrame = curDrawer.draw(canvas, curDrawerAlpha);

        }
        if (preDrawer != null && curDrawerAlpha < 1f) {
            needDrawNextFrame = true;
            preDrawer.setSize(w, h);
            preDrawer.draw(canvas, 1f - curDrawerAlpha);
        }

        if (curDrawerAlpha < 1f) {
            curDrawerAlpha += 0.04f;
            if (curDrawerAlpha > 1) {
                curDrawerAlpha = 1f;
                preDrawer = null;
            }
        }
        return needDrawNextFrame;

    }

    // TODO: 17-3-13  非 static 是否会造成内存泄露
    private class DrawThread extends Thread {

        SurfaceHolder mSurface;
        boolean mRunning = false;
        boolean mActive;
        boolean mQuit;

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    while (mSurface == null || !mRunning) {

                        //三种状态
                        if (mActive) {
                            mActive = false;
                            notify();
                        }

                        if (mQuit)
                            return;
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (!mActive) {
                            mActive = true;
                            notify();
                        }

                        final long startTime = AnimationUtils.currentAnimationTimeMillis();

                        Canvas canvas = mSurface.lockCanvas();
                        // TODO: 17-4-18 为什么是null
                        if (canvas != null) {
                            canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
                            drawSurface(canvas);
                            mSurface.unlockCanvasAndPost(canvas);

                        } else {

                            DebugLog.i("Failure locking canvas" + canvas);
                        }

                        final long drawTime = AnimationUtils.currentAnimationTimeMillis() - startTime;
                        final long needSleepTime = 16 - drawTime;
                        if (needSleepTime > 0) {
                            try {
                                Thread.sleep(needSleepTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }


    }


}
