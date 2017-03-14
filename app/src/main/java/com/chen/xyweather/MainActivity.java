package com.chen.xyweather;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.view.DynamicWeatherView;
import com.chen.xyweather.view.pager.MyViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    public static Typeface typeface;

    public static Typeface getTypeface(Context context) {
        return typeface;
    }

    @Bind(R.id.main_weather_view)
    protected DynamicWeatherView mDynamicWeatherView;

    @Bind(R.id.main_viewpager)
    protected MyViewPager mViewPager;


    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findViews() {

        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionbar() {

        if (Build.VERSION.SDK_INT >= 19) {
            //
            mViewPager.setPadding(0, 64, 0,0);
        }

    }

    @Override
    protected void setupViews() {

        initAlpha();

        loadAreaToViewPager();
    }

    /**
     *  load
     */
    private void loadAreaToViewPager() {


    }

    /**
     * 初始化alpha
     */
    private void initAlpha() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(260);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mViewPager.setAnimation(alphaAnimation);

    }
}
