package com.chen.xyweather.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.base.BaseFragment;
import com.chen.xyweather.utils.DataCleanManager;


import com.chen.xyweather.utils.MapUtil;
import com.chen.xyweather.view.drawer.BaseDrawer;

import java.io.File;
import java.util.Properties;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.lang.System.getProperties;

public class SettingActivity extends BaseActivity {
    private String result;


    @Bind(R.id.s_cache)
    protected TextView tv_cache;


    @Bind(R.id.s_notice)
    protected RelativeLayout tv_notice;

    @OnClick(R.id.s_notice)
    protected void notice() {
    }

    @Bind(R.id.s_changeskin)
    protected RelativeLayout tv_changeskin;

    @OnClick(R.id.s_changeskin)
    protected void changeskin() {

    }

    @Bind(R.id.s_cleardata)
    protected RelativeLayout tv_cleardata;

    @OnClick(R.id.s_cleardata)
    protected void cleardata() {
     //   DataCleanManager.clearAllCache(getActivity());
        tv_cache.setText("0KB");
    }


    @Bind(R.id.s_about)
    protected RelativeLayout tv_about;

    @OnClick(R.id.s_about)
    protected void about() {

    }

    @Bind(R.id.s_feedback)
    protected RelativeLayout tv_feedback;

    @OnClick(R.id.s_feedback)
    protected void feedback() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupContentView() {

    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setupActionbar() {

    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }



}
