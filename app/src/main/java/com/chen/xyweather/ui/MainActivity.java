package com.chen.xyweather.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.base.BaseFragment;
import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.utils.DepthPageTransformer;
import com.chen.xyweather.utils.UiUtil;
import com.chen.xyweather.view.DynamicWeatherView;
import com.chen.xyweather.view.DynamicWeatherViewTest;
import com.chen.xyweather.view.drawer.BaseDrawer;
import com.chen.xyweather.view.pager.MainViewPagerAdapter;
import com.chen.xyweather.view.pager.MyViewPager;
import com.chen.xyweather.view.pager.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    protected MainViewPagerAdapter viewPagerAdapter;

    protected List<BaseFragment> fragments;

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
            mViewPager.setPadding(0, UiUtil.getStatusBarHeight(), 0, 0);
        }

    }

    @Override
    protected void setupViews() {

        initAlpha();

        setupViewPager();
    }

    public void setupViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new WeatherFragment());
        fragments.add(new SettingFragment());
        viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                mDynamicWeatherView.setDrawerType(viewPagerAdapter.getItem(
//                        mViewPager.getCurrentItem()).getDrawerType());

            }
        });


//        new Thread() {
//
//            @Override
//            public void run() {
//                super.run();
//
//                mDynamicWeatherView.setDrawerType(viewPagerAdapter.getItem(
//                        mViewPager.getCurrentItem()).getDrawerType());
//
//            }
//        }.start();
//        updateCurDrawerType();
    }

    /**
     * 更新天气类型
     */
    public void updateCurDrawerType() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                DebugLog.e("test drawer chen-->");
                int position = mViewPager.getCurrentItem();
                DebugLog.e("test drawer chen--> " + position);
                BaseFragment fragment = viewPagerAdapter.getItem(position);
                BaseDrawer.Type type = fragment.getDrawerType();
                // TODO: 17-4-18
                if (type == null) {
                    DebugLog.e("test drawer null-->");
                } else {
                    DebugLog.e("test drawer chen type--> " + type);
                    mDynamicWeatherView.setDrawerType(type);
                }
            }
        }.start();
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
//                getWindow().setBackgroundDrawable(new ColorDrawable(Color.alpha(R.color.colorPrimary)));
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        DebugLog.e("activity resume");
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        DebugLog.e("activity pause");
////        mDynamicWeatherView.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        mDynamicWeatherView.onDestroy();
//    }
}
