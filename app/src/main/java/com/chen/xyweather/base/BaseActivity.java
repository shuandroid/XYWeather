package com.chen.xyweather.base;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.avos.avoscloud.AVAnalytics;
import com.chen.xyweather.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupContentView();

        findViews();

        setupActionbar();

        setupViews();

    }

    protected abstract void setupContentView();

    protected abstract void findViews();

    protected abstract void setupActionbar();

    protected abstract void setupViews();

    @Override
    protected void onPause() {
        super.onPause();
        // TODO: 17-3-6
        //统计事件
        AVAnalytics.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);// avoid mem leak
        //TODO clear views listener ,
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT == 19) {
            tintStatusBarApi19();
        } else if (Build.VERSION.SDK_INT >= 21) {
            tintStatusBarApi21();
        }
    }

    protected abstract void tintStatusBarApi21();

    protected abstract void tintStatusBarApi19();
}
